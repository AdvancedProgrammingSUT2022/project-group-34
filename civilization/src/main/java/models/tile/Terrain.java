package models.tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Terrain extends Property{

    public static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    public static HashMap<String, Terrain> allTerrains = new HashMap<>();

    public boolean canSeeBeyondHeights;

    public Terrain(HashMap<String, String> stringIntegerHashMap) {

        super();
        this.setName(stringIntegerHashMap.get("name"));
        this.setFoodRate(Integer.parseInt(stringIntegerHashMap.get("foodRate")));
        this.setGoldRate(Integer.parseInt(stringIntegerHashMap.get("goldRate")));
        this.setProductionRate(Integer.parseInt(stringIntegerHashMap.get("productionRate")));
        this.setMovingCost(Integer.parseInt(stringIntegerHashMap.get("movingCost")));
        this.setImpactOnWar(Integer.parseInt(stringIntegerHashMap.get("impactOnWar")));
        this.canSeeBeyondHeights    = Boolean.parseBoolean(stringIntegerHashMap.get("canSeeBeyondHeights"));

    }

    public static int loadDataSheet(){
        //todo ReadFromFile
        return 0;
    }

    public static int createAllInstances(){

        for (HashMap<String, String> stringIntegerHashMap : dataSheet)
            allTerrains.put(stringIntegerHashMap.get("name"),new Terrain(stringIntegerHashMap));

        return 0;
    }

}
