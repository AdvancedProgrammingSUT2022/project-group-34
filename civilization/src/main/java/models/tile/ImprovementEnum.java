package models.tile;

import models.Technology;
import models.TechnologyEnum;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum ImprovementEnum {


    Camp        ("Camp"         ,0,0,0,false, TechnologyEnum.Trapping
            , new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests}
            , new String[]{"Ivory", "Furs", "Deer"}),

    Farm        ("Farm"         ,1,0,0,false,TechnologyEnum.Agriculture
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{}
            , new String[]{"Wheat"}),

    LumberMill  ("LumberMill"   ,0,0,1,false,TechnologyEnum.Engineering
            , new Terrain[]{}
            , new Feature[]{Feature.Forests}
            , new String[]{}),

    Mine        ("Mine"         ,0,0,1,false,TechnologyEnum.Mining
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills,Terrain.Snow}
            , new Feature[]{Feature.Jungle,Feature.Forests,Feature.Marsh}
            , new String[]{"Iron", "Coal", "Gems", "Gold", "Silver"}),

    Pasture     ("Pasture"      ,0,0,0,false,TechnologyEnum.AnimalHusbandry
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new String[]{"Horses", "Cattle", "Sheep"}),

    Plantation  ("Plantation"   ,0,0,0,false,TechnologyEnum.Calendar
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert}
            , new Feature[]{Feature.Forests,Feature.Marsh,Feature.FloodPlain,Feature.Jungle}
            , new String[]{"Banana", "Silk", "Sugar", "Cotton", "Dyes", "Incense"}),

    Quarry      ("Quarry"       ,0,0,0,false,TechnologyEnum.Masonry
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Hills}
            , new Feature[]{}
            , new String[]{"Marble"}),

    TradingPost ("TradingPost"  ,0,1,0,false,TechnologyEnum.Trapping
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra}
            , new Feature[]{}
            , new String[]{}),

    Manufactory ("Manufactory"  ,0,0,3,true , TechnologyEnum.Engineering
            , new Terrain[]{Terrain.Grasslands,Terrain.Plains,Terrain.Desert,Terrain.Tundra,Terrain.Snow}
            , new Feature[]{}
            , new String[]{}),
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

    ImprovementEnum(String name, int foodRate, int goldRate, int productionRate, boolean isUsable, TechnologyEnum requiredTechnology,
                Terrain[] suitableTerrainForThisImprovement,
                Feature[] suitableFeatureForThisImprovement,
                String[] allResourcesThatNeedThisTechnology) {

        this.name = name;
        this.foodRate = foodRate;
        this.goldRate = goldRate;
        this.productionRate = productionRate;
        this.isUsable = isUsable;
        this.requiredTechnology = Technology.getAllTechnologiesCopy().get(requiredTechnology);

        this.suitableTerrainForThisImprovement.addAll(Arrays.asList(suitableTerrainForThisImprovement));

        this.suitableFeatureForThisImprovement.addAll(Arrays.asList(suitableFeatureForThisImprovement));

        HashMap<String,Resource> allResourcesCopy = Resource.getAllResourcesCopy();
        for (String resourceName : allResourcesThatNeedThisTechnology)
            this.allResourcesThatNeedThisImprovement.add(allResourcesCopy.get(resourceName));

    }



}
