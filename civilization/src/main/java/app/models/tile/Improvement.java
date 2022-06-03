package app.models.tile;

import app.models.Technology;
import app.models.TechnologyEnum;
import app.models.resource.Resource;
import app.models.resource.StrategicResource;

import java.util.ArrayList;
import java.util.HashMap;

public class Improvement{

    private final String name;
    private final int foodRate;
    private final int goldRate;
    private final int productionRate;
    private final boolean isUsable;
    private final TechnologyEnum requiredTechnology;
    private final ArrayList<Terrain> suitableTerrainForThisImprovement = new ArrayList<>();
    private final ArrayList<Feature> suitableFeatureForThisImprovement = new ArrayList<>();
    private final ArrayList<Resource> allResourcesThatNeedThisImprovement = new ArrayList<>();

    public static final HashMap<ImprovementEnum, Improvement> allImprovements = new HashMap<>();

    Improvement(ImprovementEnum improvementEnum) {

        this.name = improvementEnum.name;
        this.foodRate = improvementEnum.foodRate;
        this.goldRate = improvementEnum.goldRate;
        this.productionRate = improvementEnum.productionRate;
        this.isUsable = improvementEnum.isUsable;
        this.requiredTechnology = improvementEnum.requiredTechnology;

        this.suitableTerrainForThisImprovement.addAll(improvementEnum.suitableTerrainForThisImprovement);

        this.suitableFeatureForThisImprovement.addAll(improvementEnum.suitableFeatureForThisImprovement);

        HashMap<String,Resource> allResourcesCopy = Resource.getAllResourcesCopyString();
        for (String resourceName : improvementEnum.allResourcesThatNeedThisImprovement) {
            this.allResourcesThatNeedThisImprovement.add(allResourcesCopy.get(resourceName));
        }

    }

    public static void createAllInstances() {
        allImprovements.put(ImprovementEnum.Camp       , new Improvement(ImprovementEnum.Camp));
        allImprovements.put(ImprovementEnum.Farm       , new Improvement(ImprovementEnum.Farm));
        allImprovements.put(ImprovementEnum.LumberMill , new Improvement(ImprovementEnum.LumberMill));
        allImprovements.put(ImprovementEnum.Mine       , new Improvement(ImprovementEnum.Mine));
        allImprovements.put(ImprovementEnum.Pasture    , new Improvement(ImprovementEnum.Pasture));
        allImprovements.put(ImprovementEnum.Plantation , new Improvement(ImprovementEnum.Plantation));
        allImprovements.put(ImprovementEnum.Quarry     , new Improvement(ImprovementEnum.Quarry));
        allImprovements.put(ImprovementEnum.TradingPost, new Improvement(ImprovementEnum.TradingPost));
        allImprovements.put(ImprovementEnum.Manufactory, new Improvement(ImprovementEnum.Manufactory));
    }


    public static HashMap<ImprovementEnum, Improvement> getAllImprovements() {
        if (allImprovements.isEmpty())
            createAllInstances();
        return new HashMap<>(allImprovements);
    }


    public static int setImprovementProperties(Tile tile, Improvement improvement) {
        if (improvement == null) return 0;
        
        tile.foodRate       += improvement.foodRate;
        tile.goldRate       += improvement.goldRate;
        tile.productionRate += improvement.productionRate;
        Resource resource = tile.getResource();
        if (improvement.allResourcesThatNeedThisImprovement.contains(resource)) {
            if (!(resource instanceof StrategicResource))
                return 1;
            if (tile.civilization.getCivilizationResearchedTechnologies().containsKey(((StrategicResource)resource).getRequiredTechnology()))
                return 1;
        }

        return 0;
    }

    public static ArrayList<ImprovementEnum> getImprovementsByFeature(Feature feature){
        ArrayList<ImprovementEnum> improvements= new ArrayList<>();
        for (ImprovementEnum improvementEnum : ImprovementEnum.values())
            if (allImprovements.get(improvementEnum).suitableFeatureForThisImprovement.contains(feature))
                improvements.add(improvementEnum);

        return improvements;
    }

    public static ArrayList<ImprovementEnum> getImprovementsByTerrain(Terrain terrain){
        ArrayList<ImprovementEnum> improvements = new ArrayList<>();
        for (ImprovementEnum improvementEnum : ImprovementEnum.values())
            if (allImprovements.get(improvementEnum).suitableTerrainForThisImprovement.contains(terrain))
                improvements.add(improvementEnum);

        return improvements;
    }

    public Technology getRequiredTechnology() {
        return Technology.allTechnologies.get(requiredTechnology);
    }

    public String getName() {
        return name;
    }

    public boolean isUsable() {
        return isUsable;
    }
}
