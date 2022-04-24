package models.tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Terrain extends Property{

    static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    static HashMap<String, Terrain> allTerrains = new HashMap<>();

    public Terrain(HashMap<String, String> stringIntegerHashMap) {

        super();
        this.name           = stringIntegerHashMap.get("name");
        this.foodRate       = Integer.parseInt(stringIntegerHashMap.get("foodRate"));
        this.goldRate       = Integer.parseInt(stringIntegerHashMap.get("goldRate"));
        this.productionRate = Integer.parseInt(stringIntegerHashMap.get("productionRate"));
        this.movingCost     = Integer.parseInt(stringIntegerHashMap.get("movingCost"));
        this.impactOnWar    = Integer.parseInt(stringIntegerHashMap.get("impactOnWar"));

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
