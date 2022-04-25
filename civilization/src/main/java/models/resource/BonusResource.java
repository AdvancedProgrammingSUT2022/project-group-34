package models.resource;

import java.util.ArrayList;

public class BonusResource extends Resource{
    public BonusResource(ArrayList<String> requiredTechnologiesToBeUsable, String requiredImprovement, String name, int foodBonus, int goldBonus, int productionBonus, boolean isUsable, boolean isExchangeable) {
        super(requiredTechnologiesToBeUsable, requiredImprovement, name, foodBonus, goldBonus, productionBonus, isUsable, isExchangeable);
    }
}
