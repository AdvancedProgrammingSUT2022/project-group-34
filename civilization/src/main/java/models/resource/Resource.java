package models.resource;

import models.tile.Improvement;

import java.util.ArrayList;
import java.util.HashMap;

public class Resource {

    public static ArrayList<HashMap<String,String>> dataSheet;
    public static HashMap<String, ArrayList<String>> dataSheetLocationsOfResources;
    public static HashMap<String,Resource> allResources;

    private final String name;
    private final Improvement requiredImprovement;
    private final boolean isExchangeable;

    public Resource(String name, Improvement requiredImprovement, boolean isExchangeable) {

        this.requiredImprovement = requiredImprovement;
        this.name = name;
        this.isExchangeable = isExchangeable;
    }

    public static int loadDataSheet(){
        //todo ReadFromFile
        return 0;
    }

    public static int createAllInstances(){

        return 0;
    }

    public static HashMap<String, Resource> getAllResourcesCopy() {

        StrategicResource.allResources.forEach((name,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResources.put(name,resourceCopy);
        });
        BonusResource.allResources.forEach((name,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResources.put(name,resourceCopy);
        });
        LuxuryResource.allResources.forEach((name,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResources.put(name,resourceCopy);
        });

        return allResources;
    }





    //todo
    public Resource cloneResource(){
        return null;
    }

    public String getName() {
        return name;
    }

    public Improvement getRequiredImprovement() {
        return requiredImprovement;
    }

    public boolean isEqualsRequiredImprovement(String requiredImprovement) {
        return this.requiredImprovement.equals(requiredImprovement);
    }

    public boolean deleteResearchedTechnology(String Improvement) {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public boolean isExchangeable() {
        return isExchangeable;
    }

    public int getFoodBonus(){
        return 0;
    }

    public int getGoldBonus(){
        return 0;
    }

    public int getProductionBonus(){
        return 0;
    }

}