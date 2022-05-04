package models.tile;

import java.util.HashMap;

public enum Terrain{

    Desert      ("Desert"   ,0,0,0,-33,+1,false),
    Grassland   ("Grassland",2,0,0,-33,+1,false),
    Hills       ("Hills"    ,0,2,0,+25,+2,true ),
    Mountain    ("Mountain" ,0,0,0,+0 ,-1,true ),
    Oceana      ("Ocean"    ,0,0,0,+0 ,-1,false),
    Plain       ("Plain"    ,1,1,0,-33,+1,false),
    Snow        ("Snow"     ,0,0,0,-33,+1,false),
    Tundra      ("Tundra"   ,1,0,0,-33,+1,false),
    ;


    public static HashMap<String, Terrain> allTerrains = new HashMap<>();

    public final Object name;
    public final int foodRate;
    public final int goldRate;
    public final int productionRate;
    public final int movingCost;
    public final int impactOnWar;
    public final boolean isBlocked;

    Terrain(Object name, int foodRate, int goldRate, int productionRate, int movingCost, int impactOnWar, boolean isBlocked) {
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
        allTerrains.put("Grassland" ,Terrain.Grassland);
        allTerrains.put("Hills"     ,Terrain.Hills);
        allTerrains.put("Mountain"  ,Terrain.Mountain);
        allTerrains.put("Oceana"    ,Terrain.Oceana);
        allTerrains.put("Plain"     ,Terrain.Plain);
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
}
