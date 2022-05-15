package models.resource;

import models.tile.Improvement;

import java.util.ArrayList;
import java.util.HashMap;

public class LuxuryResource extends Resource{

    private final int goldBonus;
    private static final HashMap<String,LuxuryResource> allLuxuryResource = new HashMap<>();

    public LuxuryResource(String name, Improvement requiredImprovement, int goldBonus) {
        super(name, requiredImprovement, true);
        this.goldBonus = goldBonus;
    }



    @Override
    public Resource cloneResource(){
        LuxuryResource luxuryResource = new LuxuryResource(getName(), getRequiredImprovement(), goldBonus);
        return luxuryResource;
    }
    @Override
    public int getGoldBonus() {
        return goldBonus;
    }

    public static void createAllInstance(){

        ArrayList<Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceEnum.Cotton.getResource());
        arrayList.add(ResourceEnum.Dye.getResource());
        arrayList.add(ResourceEnum.Fur.getResource());
        arrayList.add(ResourceEnum.Gemstones.getResource());
        arrayList.add(ResourceEnum.Gold.getResource());
        arrayList.add(ResourceEnum.Eat.getResource());
        arrayList.add(ResourceEnum.Ivory.getResource());
        arrayList.add(ResourceEnum.Marble.getResource());
        arrayList.add(ResourceEnum.Silk.getResource());
        arrayList.add(ResourceEnum.Silver.getResource());
        arrayList.add(ResourceEnum.Sugar.getResource());
        for (Resource resource : arrayList)
            allLuxuryResource.put(resource.getName(), (LuxuryResource) resource);


    }

    public static HashMap<String,LuxuryResource> getAllLuxuryResource(){
        if (allLuxuryResource.isEmpty())
            createAllInstance();
        return new HashMap<>(allLuxuryResource);
    }
}
