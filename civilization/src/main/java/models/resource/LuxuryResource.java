package models.resource;

import java.util.ArrayList;

public class LuxuryResource extends Resource{

    private int goldBonus;
    private static ArrayList<LuxuryResource> allLuxuryResource = new ArrayList<>();

    public LuxuryResource(String requiredImprovement, String name, int goldBonus) {
        super(requiredImprovement, name, true, true);
        this.goldBonus = goldBonus;
    }



    @Override
    public Resource cloneResource(){
        LuxuryResource luxuryResource = new LuxuryResource(getRequiredImprovement(),getName(), goldBonus);
        return luxuryResource;
    }
    @Override
    public int getGoldBonus() {
        return goldBonus;
    }

    public static void createAllInstance(){

        allLuxuryResource.add(new LuxuryResource("farming","cotton",2));
        allLuxuryResource.add(new LuxuryResource("farming","color" ,1));
        allLuxuryResource.add(new LuxuryResource("camp","fur"  ,1));
        allLuxuryResource.add(new LuxuryResource("mine","Gemstones" ,1));
        allLuxuryResource.add(new LuxuryResource("mine","Gold" ,1));
        allLuxuryResource.add(new LuxuryResource("farming","Eat"    ,1));
        allLuxuryResource.add(new LuxuryResource("camp","ivory"  ,1));
        allLuxuryResource.add(new LuxuryResource("Stone mine","Marble" ,1));

    }

    public static ArrayList<LuxuryResource> getAllLuxuryResource(){
        if (allLuxuryResource.size() == 0)
            createAllInstance();
        return new ArrayList<>(allLuxuryResource);
    }
}
