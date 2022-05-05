package models.resource;

import java.util.ArrayList;

public class BonusResource extends Resource{

    private int foodBonus;
    private static ArrayList<BonusResource> allBonusResource = new ArrayList<>();

    public BonusResource(String requiredImprovement, String name, int foodBonus) {
        super(requiredImprovement, name, true, false);
        this.foodBonus = foodBonus;
    }

    public static void createAllInstance(){
        allBonusResource.add(new BonusResource("farming"    ,"Banana"   ,1));
        allBonusResource.add(new BonusResource("Pasture"    ,"Cow"      ,1));
        allBonusResource.add(new BonusResource("camp"   ,"Gazelle"  ,1));
        allBonusResource.add(new BonusResource("Pasture"    ,"Sheep"    ,2));
        allBonusResource.add(new BonusResource("Farm"       ,"Wheat"    ,1));
    }

    public static ArrayList<BonusResource> getAllBonusResource(){
        if (allBonusResource.size() == 0)
            createAllInstance();
        return new ArrayList<>(allBonusResource);
    }

    @Override
    public Resource cloneResource(){
        BonusResource bonusResource = new BonusResource(getRequiredImprovement(),getName(), foodBonus);
        return bonusResource;
    }
    @Override
    public int getFoodBonus() {
        return foodBonus;
    }


}
