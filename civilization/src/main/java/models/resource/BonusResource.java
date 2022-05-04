package models.resource;

public class BonusResource extends Resource{

    private int foodBonus;

    public BonusResource(String requiredImprovement, String name, int foodBouns) {
        super(requiredImprovement, name, true, false);
        this.foodBonus = foodBouns;
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
