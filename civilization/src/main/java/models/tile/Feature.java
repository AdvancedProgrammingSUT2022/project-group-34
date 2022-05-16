package models.tile;

import java.util.HashMap;

public enum Feature{

    FloodPlain  ("FloodPlain"   ,2 ,0 ,0 ,-33,+1),
    Forests     ("Forests"       ,1 ,1 ,0 ,+25,+2),
    Jungle      ("Jungle"       ,1 ,-1,0 ,+25,+2),
    Ice         ("Ice"          ,0 ,0 ,0 ,+0 ,-1),
    Marsh       ("Marsh"        ,-1,0 ,0 ,-33,+2),
    Oasis       ("Oasis"        ,3 ,0 ,1 ,-33,+1),
    ;

    private static final HashMap<String,Feature> allFeatures = new HashMap<>();

    private final String name;
    private final int foodRate;
    private final int goldRate;
    private final int productionRate;
    private final int movingCost;
    private final int impactOnWar;

    Feature(String name, int foodRate, int productionRate, int goldRate, int impactOnWar, int movingCost) {
        this.name = name;
        this.foodRate = foodRate;
        this.goldRate = goldRate;
        this.productionRate = productionRate;
        this.movingCost = movingCost;
        this.impactOnWar = impactOnWar;
    }

    private static void createAllInstances(){
        for (Feature feature : Feature.values())
            allFeatures.put(feature.name,feature);
    }

    public static HashMap<String, Feature> getAllFeatures() {
        if (allFeatures.isEmpty())
            createAllInstances();
        return new HashMap<>(allFeatures);
    }

    static void setFeatureProperties(Tile tile,Feature feature) {

        if (feature == null)
            return;
        if(feature.name.equals("Forest")){
            tile.foodRate = 1;
            tile.goldRate = 1;
        }
        else{
            tile.foodRate += feature.foodRate;
            tile.goldRate += feature.goldRate;
        }

        tile.productionRate += feature.productionRate;
        tile.impactOnWar    += feature.impactOnWar;

        if (feature.movingCost == -1 || tile.movingCost == -1)
            tile.movingCost = -1;
        else
            tile.movingCost += feature.movingCost;

    }

    public String getName() {
        return name;
    }
}
