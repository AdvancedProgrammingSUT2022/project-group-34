package models.tile;

import models.Technology;
import models.resource.Resource;


import java.util.ArrayList;
import java.util.HashMap;

public enum Improvement{


    Camp("Camp",0,0,0,false,"Trapping"
            ,new String[]{"asd"},new String[]{"asd"}, new String[]{"asd"}),
    Farm("Farm",0,0,0,false,"Trapping"
                 ,new String[]{"asd"},new String[]{"asd"}, new String[]{"asd"}),
    LumberMill("LumberMill",0,0,0,false,"Trapping"
                 ,new String[]{"asd"},new String[]{"asd"}, new String[]{"asd"}),
    ;


    private String name;
    private int foodRate;
    private int goldRate;
    private int productionRate;
    private boolean isUsable;
    private Technology requiredTechnology = null;
    private ArrayList<Terrain> suitableTerrainForThisImprovement;
    private ArrayList<Feature> suitableFeatureForThisImprovement;
    private ArrayList<Resource> allResourcesThatNeedThisTechnology;

    public static HashMap<String, Improvement> allImprovements = new HashMap<>();

    Improvement(String name, int foodRate, int goldRate, int productionRate, boolean isUsable, String requiredTechnology,
                String[] suitableTerrainForThisImprovement,
                String[] suitableFeatureForThisImprovement,
                String[] allResourcesThatNeedThisTechnology) {

        this.name = name;
        this.foodRate = foodRate;
        this.goldRate = goldRate;
        this.productionRate = productionRate;
        this.isUsable = isUsable;
        this.requiredTechnology = Technology.getAllTechnologiesCopy().get(requiredTechnology);

        for (String TerrainName : suitableTerrainForThisImprovement)
            this.suitableTerrainForThisImprovement.add(Terrain.getAllTerrains().get(TerrainName));

        for (String featureName : suitableFeatureForThisImprovement)
            this.suitableFeatureForThisImprovement.add(Feature.getAllFeatures().get(featureName));

        for (String resourceName : allResourcesThatNeedThisTechnology)
            this.allResourcesThatNeedThisTechnology.add(Resource.getAllResourcesCopy().get(resourceName));

    }

    public static void createAllInstances() {
        allImprovements.put("Camp",Improvement.Camp);
        allImprovements.put("Farm",Improvement.Farm);
        allImprovements.put("LumberMill",Improvement.LumberMill);
    }


    public static HashMap<String, Improvement> getAllImprovements() {
        if (allImprovements.isEmpty())
            createAllInstances();
        return new HashMap<>(allImprovements);
    }


    public static int setImprovementProperties(Tile tile, Improvement improvement) {
        tile.foodRate       += improvement.foodRate;
        tile.goldRate       += improvement.goldRate;
        tile.productionRate += improvement.productionRate;

        if (improvement.allResourcesThatNeedThisTechnology.contains(tile.getResource()))
            if (tile.getResource().isVisible())
                return 1;

        return 0;
    }
}
