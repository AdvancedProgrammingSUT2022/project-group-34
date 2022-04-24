package models.resource;

import models.tile.Terrain;

import java.util.ArrayList;
import java.util.HashMap;

public class Resource {

    public static ArrayList<HashMap<String,String>> dataSheet;
    public static HashMap<String, ArrayList<String>> dataSheetLocationsOfResources;
    public static HashMap<String,Resource> allResources;

    private ArrayList<String> requiredTechnologiesToBeUsable;
    private String requiredImprovement;
    private String name;
    private int foodBonus;
    private int goldBonus;
    private int productionBonus;
    private boolean isUsable = false;
    private boolean isExchangeable;

    public Resource(ArrayList<String> requiredTechnologiesToBeUsable, String requiredImprovement, String name, int foodBonus, int goldBonus, int productionBonus, boolean isUsable, boolean isExchangeable) {
        
        this.requiredTechnologiesToBeUsable = requiredTechnologiesToBeUsable;
        this.requiredImprovement = requiredImprovement;
        this.name = name;
        this.foodBonus = foodBonus;
        this.goldBonus = goldBonus;
        this.productionBonus = productionBonus;
        this.isUsable = isUsable;
        this.isExchangeable = isExchangeable;
    }

    public static int loadDataSheet(){
        //todo ReadFromFile
        return 0;
    }

    public static int createAllInstances(){
        


        return 0;
    }

    public void removeResearchedTechnologyToBeUsable(String technologyName) {
        requiredTechnologiesToBeUsable.remove(technologyName);
    }


}