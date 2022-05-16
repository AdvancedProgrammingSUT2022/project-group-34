package models.tile;

import models.Technology;
import models.resource.Resource;
import models.resource.ResourceName;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum Improvement{


    Camp        ("Camp"         ,0,0,0,false,"Trapping"
            , new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests}
            , new ResourceName[]{ResourceName.Ivory,ResourceName.Furs,ResourceName.Deer}),

    Farm        ("Farm"         ,1,0,0,false,"Agriculture"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Wheat}),

    LumberMill  ("LumberMill"   ,0,0,1,false,"Engineering"
            , new Terrain[]{}
            , new Feature[]{Feature.Forests}
            , new ResourceName[]{}),

    Mine        ("Mine"         ,0,0,1,false,"Mining"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills,Terrain.Snow}
            , new Feature[]{Feature.Jungle,Feature.Forests,Feature.Marsh}
            , new ResourceName[]{ResourceName.Iron,ResourceName.Coal,ResourceName.Gems,ResourceName.Gold,ResourceName.Silver}),

    Pasture     ("Pasture"      ,0,0,0,false,"AnimalHusbandry"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Horses,ResourceName.Cattle,ResourceName.Sheep}),

    Plantation  ("Plantation"   ,0,0,0,false,"Calendar"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests,Feature.Marsh,Feature.FloodPlane,Feature.Jungle}
            , new ResourceName[]{ResourceName.Banana,ResourceName.Silk,ResourceName.Sugar,ResourceName.Cotton,ResourceName.Dyes,ResourceName.Incense}),

    Quarry      ("Quarry"       ,0,0,0,false,"Masonry"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Marble}),

    TradingPost ("TradingPost"  ,0,1,0,false,"Trapping"
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra}
            , new Feature[]{}
            , new ResourceName[]{}),

    Manufactory ("Manufactory"  ,0,0,3,true ,null
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Snow}
            , new Feature[]{}
            , new ResourceName[]{}),
    ;


    private final String name;
    private final int foodRate;
    private final int goldRate;
    private final int productionRate;
    private final boolean isUsable;
    private final Technology requiredTechnology;
    private final ArrayList<Terrain> suitableTerrainForThisImprovement = new ArrayList<>();
    private final ArrayList<Feature> suitableFeatureForThisImprovement = new ArrayList<>();
    private final ArrayList<Resource> allResourcesThatNeedThisImprovement = new ArrayList<>();

    public static final HashMap<String, Improvement> allImprovements = new HashMap<>();

    Improvement(String name, int foodRate, int goldRate, int productionRate, boolean isUsable, String requiredTechnology,
                Terrain[] suitableTerrainForThisImprovement,
                Feature[] suitableFeatureForThisImprovement,
                ResourceName[] allResourcesThatNeedThisTechnology) {

        this.name = name;
        this.foodRate = foodRate;
        this.goldRate = goldRate;
        this.productionRate = productionRate;
        this.isUsable = isUsable;
        this.requiredTechnology = Technology.getAllTechnologiesCopy().get(requiredTechnology);

        this.suitableTerrainForThisImprovement.addAll(Arrays.asList(suitableTerrainForThisImprovement));

        this.suitableFeatureForThisImprovement.addAll(Arrays.asList(suitableFeatureForThisImprovement));

        HashMap<String,Resource> allResourcesCopy = Resource.getAllResourcesCopy();
        for (ResourceName resourceName : allResourcesThatNeedThisTechnology)
            this.allResourcesThatNeedThisImprovement.add(allResourcesCopy.get(resourceName.name));

    }

    public static void createAllInstances() {
        allImprovements.put(Improvement.Camp.name       ,Improvement.Camp);
        allImprovements.put(Improvement.Farm.name       ,Improvement.Farm);
        allImprovements.put(Improvement.LumberMill.name ,Improvement.LumberMill);
        allImprovements.put(Improvement.Mine.name       ,Improvement.Mine);
        allImprovements.put(Improvement.Pasture.name    ,Improvement.Pasture);
        allImprovements.put(Improvement.Plantation.name ,Improvement.Plantation);
        allImprovements.put(Improvement.Quarry.name     ,Improvement.Quarry);
        allImprovements.put(Improvement.TradingPost.name,Improvement.TradingPost);
        allImprovements.put(Improvement.Manufactory.name,Improvement.Manufactory);
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

        if (improvement.allResourcesThatNeedThisImprovement.contains(tile.getResource()))
            if (tile.getResource().isVisible())
                return 1;

        return 0;
    }

    public static ArrayList<String> getImprovementsByFeature(Feature feature){
        ArrayList<String> improvements= new ArrayList<>();
        for (Improvement improvement : Improvement.values())
            if (improvement.suitableFeatureForThisImprovement.contains(feature))
                improvements.add(improvement.getName());

        return improvements;
    }

    public static ArrayList<String> getImprovementsByTerrain(Terrain terrain){
        ArrayList<String> improvements = new ArrayList<>();
        for (Improvement improvement : Improvement.values())
            if (improvement.suitableTerrainForThisImprovement.contains(terrain))
                improvements.add(improvement.getName());

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
