package models.resource;

import models.TechnologyEnum;
import models.tile.Improvement;

import java.util.ArrayList;
import java.util.HashMap;

public class StrategicResource extends Resource{

    private static final HashMap<String,StrategicResource> allStrategicResource = new HashMap<>();
    private final TechnologyEnum requiredTechnology;
    private final int productionBonus;

    public StrategicResource(String name, Improvement requiredImprovement, int productionBonus, TechnologyEnum requiredTechnology) {
        super(name, requiredImprovement, true);
        this.productionBonus = productionBonus;
        this.requiredTechnology = requiredTechnology;
    }

    public static void createAllInstance(){
        ArrayList<Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceEnum.Coal.getResource());
        arrayList.add(ResourceEnum.Horses.getResource());
        arrayList.add(ResourceEnum.Iron.getResource());
        for (Resource resource : arrayList)
            allStrategicResource.put(resource.getName(), (StrategicResource) resource);
    }

    public static HashMap<String,StrategicResource> getAllLuxuryResource(){
        if (allStrategicResource.isEmpty())
            createAllInstance();
        return allStrategicResource;
    }


    @Override
    public Resource cloneResource(){
        return new StrategicResource(getName(),getRequiredImprovement(), productionBonus, requiredTechnology);
    }

    @Override
    public int getProductionBonus(){
        return productionBonus;
    }

    public boolean isVisible() {
        return requiredTechnology == null;
    }

}
