package models.tile;

import models.Technology;
import models.TechnologyEnum;
import models.resource.Resource;
import models.resource.ResourceName;
import models.resource.StrategicResource;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Improvement{

    private final String name;
    private final int foodRate;
    private final int goldRate;
    private final int productionRate;
    private final boolean isUsable;
    private final Technology requiredTechnology;
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
        this.requiredTechnology = Technology.getAllTechnologiesCopy().get(improvementEnum.requiredTechnology);

        this.suitableTerrainForThisImprovement.addAll(improvementEnum.suitableTerrainForThisImprovement);

        this.suitableFeatureForThisImprovement.addAll(improvementEnum.suitableFeatureForThisImprovement);

        HashMap<String,Resource> allResourcesCopy = Resource.getAllResourcesCopy();
        for (Resource resource : improvementEnum.allResourcesThatNeedThisImprovement)
            this.allResourcesThatNeedThisImprovement.add(resource);

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

    public static ArrayList<String> getImprovementsByFeature(Feature feature){
        ArrayList<String> improvements= new ArrayList<>();
        for (ImprovementEnum improvementEnum : ImprovementEnum.values())
            if (allImprovements.get(improvementEnum).suitableFeatureForThisImprovement.contains(feature))
                improvements.add(improvementEnum.name);

        return improvements;
    }

    public static ArrayList<String> getImprovementsByTerrain(Terrain terrain){
        ArrayList<String> improvements = new ArrayList<>();
        for (ImprovementEnum improvementEnum : ImprovementEnum.values())
            if (allImprovements.get(improvementEnum).suitableTerrainForThisImprovement.contains(terrain))
                improvements.add(improvementEnum.name);

        return improvements;
    }

    public Technology getRequiredTechnology() {
        return requiredTechnology;
    }

    public String getName() {
        return name;
    }

    public boolean isUsable() {
        return isUsable;
    }
}
