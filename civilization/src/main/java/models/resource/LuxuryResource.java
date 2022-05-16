package models.resource;

import models.tile.ImprovementEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class LuxuryResource extends Resource{

    private final int goldBonus;
    private static final HashMap<String,LuxuryResource> allLuxuryResource = new HashMap<>();

    public LuxuryResource(String name, ImprovementEnum requiredImprovement, int goldBonus) {
        super(name, requiredImprovement, true);
        this.goldBonus = goldBonus;
    }



    @Override
    public Resource cloneResource(){
        return new LuxuryResource(getName(), getRequiredImprovement(), goldBonus);
    }
    @Override
    public int getGoldBonus() {
        return goldBonus;
    }

    public static void createAllInstance(){

        ArrayList<Resource> arrayList = new ArrayList<>();
        arrayList.add(ResourceData.Cotton.getResource());
        arrayList.add(ResourceData.Dyes.getResource());
        arrayList.add(ResourceData.Furs.getResource());
        arrayList.add(ResourceData.Gems.getResource());
        arrayList.add(ResourceData.Gold.getResource());
        arrayList.add(ResourceData.Incense.getResource());
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
