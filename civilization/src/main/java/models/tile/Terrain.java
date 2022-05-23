package models.tile;

import java.util.HashMap;

public enum Terrain{

    Desert      ("Desert"   ,0,0,0,-33,+1,false),
    Grasslands   ("Grasslands",2,0,0,-33,+1,false),
    Hills       ("Hills"    ,0,2,0,+25,+2,true ),
    Mountain    ("Mountain" ,0,0,0,+0 ,-1,true ),
    Ocean       ("Ocean"    ,0,0,0,+0 ,-1,true),
    Plains      ("Plains"    ,1,1,0,-33,+1,false),
    Snow        ("Snow"     ,0,0,0,-33,+1,false),
    Tundra      ("Tundra"   ,1,0,0,-33,+1,false),
    ;


    public static HashMap<String, Terrain> allTerrains = new HashMap<>();

    private final String name;
    private final int foodRate;
    private final int goldRate;
    private final int productionRate;
    private final int movingCost;
    private final int impactOnWar;
    private final boolean isBlocked;

    Terrain(String name, int foodRate, int goldRate, int productionRate, int movingCost, int impactOnWar, boolean isBlocked) {
        this.name = name;
        this.foodRate = foodRate;
        this.goldRate = goldRate;
        this.productionRate = productionRate;
        this.movingCost = movingCost;
        this.impactOnWar = impactOnWar;
        this.isBlocked = isBlocked;
    }

    private static void createAllInstances(){

        allTerrains.put("Desert"    ,Terrain.Desert);
        allTerrains.put("Grasslands",Terrain.Grasslands);
        allTerrains.put("Hills"     ,Terrain.Hills);
        allTerrains.put("Mountain"  ,Terrain.Mountain);
        allTerrains.put("Oceana"    ,Terrain.Ocean);
        allTerrains.put("Plains"    ,Terrain.Plains);
        allTerrains.put("Snow"      ,Terrain.Snow);
        allTerrains.put("Tundra"    ,Terrain.Tundra);
    }

    public static HashMap<String, Terrain> getAllTerrains() {
        if (allTerrains.isEmpty())
            createAllInstances();
        return new HashMap<>(allTerrains);
    }

    static void setTerrainProperties(Tile tile, Terrain terrain) {

        if (terrain == null) return;

        tile.foodRate       += terrain.foodRate;
        tile.goldRate       += terrain.goldRate;
        tile.productionRate += terrain.productionRate;
        tile.impactOnWar    += terrain.impactOnWar;
        tile.movingCost     += terrain.movingCost;
        tile.isBlock |= terrain.isBlocked;
    }

    public String getName() {
        return name;
    }
}
