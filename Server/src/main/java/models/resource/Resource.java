package models.resource;

import models.tile.ImprovementEnum;

import java.util.HashMap;

public class Resource {

    private final String name;
    private final ImprovementEnum requiredImprovement;
    private final boolean isExchangeable;

    public Resource(String name, ImprovementEnum requiredImprovement, boolean isExchangeable) {

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

    public static HashMap<String, Resource> getAllResourcesCopyString() {
        HashMap<ResourceEnum, Resource> resourceEnumResourceHashMap = getAllResourcesCopy();
        HashMap<String      , Resource> resourceHashMap = new HashMap<>();
        resourceEnumResourceHashMap.forEach((resourceEnum, resource) -> resourceHashMap.put(resource.getName(), resource));

        return resourceHashMap;
    }
    public static HashMap<ResourceEnum, Resource> getAllResourcesCopy() {

        HashMap<ResourceEnum, Resource> allResourcesCopy = new HashMap<>();

        StrategicResource.getAllStrategicResource().forEach((resourceEnum,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResourcesCopy.put(resourceEnum,resourceCopy);
        });
        BonusResource.getAllBonusResource().forEach((ResourceEnum,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResourcesCopy.put(ResourceEnum,resourceCopy);
        });
        LuxuryResource.getAllLuxuryResource().forEach((resourceEnum,resource)->{
            Resource resourceCopy = resource.cloneResource();
            allResourcesCopy.put(resourceEnum,resourceCopy);
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

    public ImprovementEnum getRequiredImprovement() {
        return requiredImprovement;
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