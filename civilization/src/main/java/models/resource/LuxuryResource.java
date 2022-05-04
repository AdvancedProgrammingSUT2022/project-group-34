package models.resource;

public class LuxuryResource extends Resource{

    private int goldBonus;

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
}
