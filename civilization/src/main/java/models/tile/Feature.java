package models.tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Feature extends Property{

    static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    static HashMap<String, Feature> allFeatures = new HashMap<>();


    public Feature(HashMap<String, String> stringIntegerHashMap) {

        super();
        this.name = stringIntegerHashMap.get("name");
        foodRate = Integer.parseInt(stringIntegerHashMap.get("foodRate"));
        goldRate = Integer.parseInt(stringIntegerHashMap.get("goldRate"));
        productionRate = Integer.parseInt(stringIntegerHashMap.get("productionRate"));
        movingCost = Integer.parseInt(stringIntegerHashMap.get("movingCost"));
        impactOnWar = Integer.parseInt(stringIntegerHashMap.get("impactOnWar"));

    }

    public static int loadDataSheet(){
        //ReadFromFile
        return 0;
    }

    public static int createAllInstances(){

        for (HashMap<String, String> stringIntegerHashMap : dataSheet)
            allFeatures.put(stringIntegerHashMap.get("name"),new Feature(stringIntegerHashMap));

        return 0;
    }
}
