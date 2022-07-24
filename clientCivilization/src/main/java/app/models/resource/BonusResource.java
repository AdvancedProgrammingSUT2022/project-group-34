package app.models.resource;

import app.models.tile.ImprovementEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class BonusResource extends Resource{

    private final int foodBonus;
    public static final HashMap<ResourceEnum,BonusResource> allBonusResource = new HashMap<>();

    public BonusResource(String name, ImprovementEnum requiredImprovement, int foodBonus) {
        super(name, requiredImprovement, false);
        this.foodBonus = foodBonus;
    }

    public static void createAllInstance(){
        ArrayList <Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceData.Banana.getResource());
        arrayList.add(ResourceData.Cattle.getResource());
        arrayList.add(ResourceData.Deer.getResource());
        arrayList.add(ResourceData.Sheep.getResource());
        arrayList.add(ResourceData.Wheat.getResource());
        for (Resource resource : arrayList) {
            allBonusResource.put(ResourceEnum.valueOf(resource.getName()), (BonusResource) resource);
        }
    }

    public static HashMap<ResourceEnum,BonusResource> getAllBonusResource(){
        if (allBonusResource.isEmpty())
            createAllInstance();
        return allBonusResource;
    }

    public Resource cloneResource(){
        return new BonusResource(getName(), getRequiredImprovement(), foodBonus);
    }
    @Override
    public int getFoodBonus() {
        return foodBonus;
    }


}
