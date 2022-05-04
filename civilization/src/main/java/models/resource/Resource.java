package models.resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Resource {

    public static ArrayList<HashMap<String,String>> dataSheet;
    public static HashMap<String, ArrayList<String>> dataSheetLocationsOfResources;
    public static HashMap<String,Resource> allResources;

    private String name;
    private String requiredImprovement;
    private boolean isVisible;
    private boolean isExchangeable;

    public Resource(String requiredImprovement, String name, boolean isVisible, boolean isExchangeable) {

        this.requiredImprovement = requiredImprovement;
        this.name = name;
        this.isVisible= isVisible;
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

        HashMap<String, Resource> allResourcesCopy = new HashMap<>();

        allResources.forEach((name,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResourcesCopy.put(name,resourceCopy);
        });

        return allResourcesCopy;
    }





    //todo
    public Resource cloneResource(){
        return null;
    }

    public String getName() {
        return name;
    }

    public String getRequiredImprovement() {
        return requiredImprovement;
    }

    public void setRequiredImprovement(String requiredImprovement) {
        this.requiredImprovement = requiredImprovement;
    }

    public boolean isEqualsRequiredImprovement(String requiredImprovement) {
        return this.requiredImprovement.equals(requiredImprovement);
    }

    public boolean deleteResearchedTechnology(String Improvement) {
        return false;
    }

    public boolean isVisible() {
        return isVisible;
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