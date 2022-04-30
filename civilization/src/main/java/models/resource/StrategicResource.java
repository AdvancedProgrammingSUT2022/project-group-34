package models.resource;

public class StrategicResource extends Resource{

    private String requiredTechnology;
    private boolean isVisible;
    private int productionBonus;

    public StrategicResource(String requiredImprovement, String name, int productionBonus, String requiredTechnology) {
        super(requiredImprovement, name, false, true);
        this.productionBonus = productionBonus;
        this.requiredTechnology = requiredTechnology;
    }

    public boolean deleteResearchedTechnology(String technology) {
        if (this.isVisible)
            return false;
        if (this.requiredTechnology.equals(technology)){
            this.isVisible = true;
            return true;
        }
        return false;
    }

    @Override
    public Resource clonResource(){
        StrategicResource strategicResource = new StrategicResource(getRequiredImprovement(),getName(), productionBonus, requiredTechnology);
        strategicResource.isVisible = this.isVisible;
        return strategicResource;
    }

    @Override
    public int getProductionBonus(){
        return productionBonus;
    }
}
