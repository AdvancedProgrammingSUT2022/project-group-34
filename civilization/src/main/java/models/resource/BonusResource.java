package models.resource;

import models.tile.Improvement;

import java.util.ArrayList;
import java.util.HashMap;

public class BonusResource extends Resource{

    private final int foodBonus;
    private static final HashMap<String,BonusResource> allBonusResource = new HashMap<>();

    public BonusResource(String name, Improvement requiredImprovement, int foodBonus) {
        super(name, requiredImprovement, false);
        this.foodBonus = foodBonus;
    }

    public static void createAllInstance(){
        ArrayList <Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceEnum.Banana.getResource());
        arrayList.add(ResourceEnum.Cow.getResource());
        arrayList.add(ResourceEnum.Gazelle.getResource());
        arrayList.add(ResourceEnum.Sheep.getResource());
        arrayList.add(ResourceEnum.Wheat.getResource());
        for (Resource resource : arrayList) {
            allBonusResource.put(resource.getName(),(BonusResource) resource);
        }
    }

    public static HashMap<String,BonusResource> getAllBonusResource(){
        if (allBonusResource.isEmpty())
            createAllInstance();
        return allBonusResource;
    }

    @Override
    public Resource cloneResource(){
        BonusResource bonusResource = new BonusResource(getName(), getRequiredImprovement(), foodBonus);
        return bonusResource;
    }
    @Override
    public int getFoodBonus() {
        return foodBonus;
    }


}
