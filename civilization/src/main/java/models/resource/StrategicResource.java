package models.resource;

import java.util.ArrayList;

public class StrategicResource extends Resource{

    private ArrayList<String> requiredTechnologiesToBeVisible;
    private boolean isVisible;

    public StrategicResource(ArrayList<String> requiredTechnologiesToBeUsable, String requiredImprovement, String name, int foodBonus, int goldBonus, int productionBonus, boolean isUsable, boolean isExchangeable) {
        super(requiredTechnologiesToBeUsable, requiredImprovement, name, foodBonus, goldBonus, productionBonus, isUsable, isExchangeable);
        isVisible = false;
    }


    public void removeResearchedTechnologyToBeVisible(String technologyName) {
        requiredTechnologiesToBeVisible.remove(technologyName);
        removeResearchedTechnologyToBeVisible(technologyName);
    }
}
