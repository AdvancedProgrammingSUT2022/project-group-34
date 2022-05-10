package models.resource;

import java.util.ArrayList;

public class StrategicResource extends Resource{

    private static ArrayList<StrategicResource> allStrategicResource = new ArrayList<>();
    private String requiredTechnology;
    private boolean isVisible;
    private int productionBonus;

    public StrategicResource(String name, String requiredImprovement, int productionBonus, String requiredTechnology) {
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
    public Resource cloneResource(){
        StrategicResource strategicResource = new StrategicResource(getRequiredImprovement(),getName(), productionBonus, requiredTechnology);
        strategicResource.isVisible = this.isVisible;
        return strategicResource;
    }

    @Override
    public int getProductionBonus(){
        return productionBonus;
    }


    public static void createAllInstance(){
        allStrategicResource.add(new StrategicResource("Coal"   ,"Mine"     ,1,""));
        allStrategicResource.add(new StrategicResource("Horse"  ,"Pasture"  ,1,""));
        allStrategicResource.add(new StrategicResource("Iron"   ,"Mine"     ,1,""));
    }

    public static ArrayList<StrategicResource> getAllLuxuryResource(){
        if (allStrategicResource.size() == 0)
            createAllInstance();
        return new ArrayList<>(allStrategicResource);
    }
}
