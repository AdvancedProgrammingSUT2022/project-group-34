package models.tile;

import models.Technology;
import models.TechnologyEnum;
import models.resource.Resource;
import models.resource.ResourceName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum ImprovementEnum {


    Camp        ("Camp"         ,0,0,0,false, TechnologyEnum.Trapping
            , new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests}
            , new ResourceName[]{ResourceName.Ivory,ResourceName.Furs,ResourceName.Deer}),

    Farm        ("Farm"         ,1,0,0,false,TechnologyEnum.Agriculture
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Wheat}),

    LumberMill  ("LumberMill"   ,0,0,1,false,TechnologyEnum.Engineering
            , new Terrain[]{}
            , new Feature[]{Feature.Forests}
            , new ResourceName[]{}),

    Mine        ("Mine"         ,0,0,1,false,TechnologyEnum.Mining
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills,Terrain.Snow}
            , new Feature[]{Feature.Jungle,Feature.Forests,Feature.Marsh}
            , new ResourceName[]{ResourceName.Iron,ResourceName.Coal,ResourceName.Gems,ResourceName.Gold,ResourceName.Silver}),

    Pasture     ("Pasture"      ,0,0,0,false,TechnologyEnum.AnimalHusbandry
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Horses,ResourceName.Cattle,ResourceName.Sheep}),

    Plantation  ("Plantation"   ,0,0,0,false,TechnologyEnum.Calendar
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests,Feature.Marsh,Feature.FloodPlane,Feature.Jungle}
            , new ResourceName[]{ResourceName.Banana,ResourceName.Silk,ResourceName.Sugar,ResourceName.Cotton,ResourceName.Dyes,ResourceName.Incense}),

    Quarry      ("Quarry"       ,0,0,0,false,TechnologyEnum.Masonry
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new ResourceName[]{ResourceName.Marble}),

    TradingPost ("TradingPost"  ,0,1,0,false,TechnologyEnum.Trapping
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra}
            , new Feature[]{}
            , new ResourceName[]{}),

    Manufactory ("Manufactory"  ,0,0,3,true , TechnologyEnum.Engineering
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Snow}
            , new Feature[]{}
            , new ResourceName[]{}),
    ;


    public final String name;
    public final int foodRate;
    public final int goldRate;
    public final int productionRate;
    public final boolean isUsable;
    public final Technology requiredTechnology;
    public final ArrayList<Terrain> suitableTerrainForThisImprovement = new ArrayList<>();
    public final ArrayList<Feature> suitableFeatureForThisImprovement = new ArrayList<>();
    public final ArrayList<Resource> allResourcesThatNeedThisImprovement = new ArrayList<>();

    public static final HashMap<String, Improvement> allImprovements = new HashMap<>();

    ImprovementEnum(String name, int foodRate, int goldRate, int productionRate, boolean isUsable, TechnologyEnum requiredTechnology,
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


}
