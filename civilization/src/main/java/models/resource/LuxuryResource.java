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
        arrayList.add(ResourceData.Cotton.getResource());
        arrayList.add(ResourceData.Dyes.getResource());
        arrayList.add(ResourceData.Fur.getResource());
        arrayList.add(ResourceData.Gemstones.getResource());
        arrayList.add(ResourceData.Gold.getResource());
        arrayList.add(ResourceData.Eat.getResource());
        arrayList.add(ResourceData.Ivory.getResource());
        arrayList.add(ResourceData.Marble.getResource());
        arrayList.add(ResourceData.Silk.getResource());
        arrayList.add(ResourceData.Silver.getResource());
        arrayList.add(ResourceData.Sugar.getResource());
        for (Resource resource : arrayList)
            allLuxuryResource.put(resource.getName(), (LuxuryResource) resource);


    }

    public static HashMap<String,LuxuryResource> getAllLuxuryResource(){
        if (allLuxuryResource.isEmpty())
            createAllInstance();
        return new HashMap<>(allLuxuryResource);
    }
}
