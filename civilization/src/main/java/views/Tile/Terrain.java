package views.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Terrain extends Property{

    static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    static HashMap<String, Terrain> allTerrains = new HashMap<>();

    public Terrain(HashMap<String, String> stringIntegerHashMap) {

        super();
        this.name = stringIntegerHashMap.get("name");
        foodRate = Integer.parseInt(stringIntegerHashMap.get("foodRate"));
        goldRate = Integer.parseInt(stringIntegerHashMap.get("goldRate"));
        productionRate = Integer.parseInt(stringIntegerHashMap.get("productionRate"));
        movingCost = Integer.parseInt(stringIntegerHashMap.get("movingCost"));
        impactOnWar = Integer.parseInt(stringIntegerHashMap.get("impactOnWar"));

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
