package models.tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Improvement extends Property{

    static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    static HashMap<String, Improvement> allImprovements = new HashMap<>();

    private ArrayList<String> requiredTechnologiesToBeUsed = new ArrayList<>();
    private boolean isUsable;

    public Improvement(HashMap<String,String> stringStringHashMap) {

        this.name = stringStringHashMap.get("name");
        this.foodRate = Integer.parseInt(stringStringHashMap.get("foodRate"));
        this.goldRate = Integer.parseInt(stringStringHashMap.get("goldRate"));
        this.productionRate = Integer.parseInt(stringStringHashMap.get("productionRate"));
        this.movingCost = Integer.parseInt(stringStringHashMap.get("movingCost"));
        this.impactOnWar = Integer.parseInt(stringStringHashMap.get("impactOnWar"));
        int numberOfRequiredTechnologiesToBeUsed = Integer.parseInt(stringStringHashMap.get("numberOfRequiredTechnologiesToBeUsed"));

        for (int i = 0; i < numberOfRequiredTechnologiesToBeUsed; i++)
            this.requiredTechnologiesToBeUsed.add("Technology" + (i + 1));

        this.isUsable = false;
    }


    public static int loadDataSheet(){
        //ReadFromFile
        return 0;
    }

    public static int createAllInstances(){

        for (HashMap<String, String> stringIntegerHashMap : dataSheet)
            allImprovements.put(stringIntegerHashMap.get("name"), new Improvement(stringIntegerHashMap));

        return 0;
    }

    public ArrayList<String> getRequiredTechnologyToBeUsed() {
        return requiredTechnologiesToBeUsed;
    }

    public void setRequiredTechnologyToBeUsed(ArrayList<String> requiredTechnologiesToBeUsed) {
        this.requiredTechnologiesToBeUsed = requiredTechnologiesToBeUsed;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public void removeSearchedTechnology(String nameTechnology){

        if (!requiredTechnologiesToBeUsed.contains(nameTechnology))
            return;
        requiredTechnologiesToBeUsed.remove(nameTechnology);
        if (requiredTechnologiesToBeUsed.size() == 0)
            isUsable = true;

    }
}
