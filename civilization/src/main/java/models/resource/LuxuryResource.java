package models.resource;

import java.util.ArrayList;
import java.util.HashMap;

public class LuxuryResource extends Resource{

    private Boolean isPointAdded;

    public LuxuryResource(String name, ArrayList<String> requiredTechnologiesToBeUsable, String requiredImprovement,
                          int foodBonus, int goldBonus, int productionBonus, boolean isUsable, boolean isExchangeable) {
        super(requiredTechnologiesToBeUsable, requiredImprovement, name, foodBonus, goldBonus, productionBonus, isUsable, isExchangeable);
        isPointAdded = false;
    }

    public Boolean getPointAdded() {
        return isPointAdded;
    }

    public void setPointAdded(Boolean pointAdded) {
        isPointAdded = pointAdded;
    }
}
