package models.resource;

import models.TechnologyEnum;
import models.tile.ImprovementEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class StrategicResource extends Resource{

    public static final HashMap<ResourceEnum,StrategicResource> allStrategicResource = new HashMap<>();
    private final TechnologyEnum requiredTechnology;
    private final int productionBonus;

    public StrategicResource(String name, ImprovementEnum requiredImprovement, int productionBonus, TechnologyEnum requiredTechnology) {
        super(name, requiredImprovement, true);
        this.productionBonus = productionBonus;
        this.requiredTechnology = requiredTechnology;
    }

    public static void createAllInstance(){
        ArrayList<Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceData.Coal.getResource());
        arrayList.add(ResourceData.Horses.getResource());
        arrayList.add(ResourceData.Iron.getResource());
        for (Resource resource : arrayList)
            allStrategicResource.put(ResourceEnum.valueOf(resource.getName()), (StrategicResource) resource);
    }

    public static HashMap<ResourceEnum,StrategicResource> getAllStrategicResource(){
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

    public TechnologyEnum getRequiredTechnology() {
        return requiredTechnology;
    }

}
