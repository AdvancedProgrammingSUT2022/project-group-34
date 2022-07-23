package app.models.tile;

import app.models.resource.Resource;
import app.models.unit.Unit;

public class Tile extends AbstractTile {

    private Improvement improvement = null;
    private Resource resource = null;
    boolean isUsableResource = false;

    int foodRate;
    int goldRate;
    int productionRate;
    int impactOnWar;
    private boolean hasRoad = false;
    private boolean hasRail = false;
    private boolean isLooted = false;
    private Unit
            NonCombatUnit = null;
    private Unit
            combatUnit = null;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }


    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public void setHasRail(boolean hasRail) {
        this.hasRail = hasRail;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public boolean hasRail() {
        return hasRail;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public boolean isBlock() {
        return isBlock;
    }

    @Override
    public Unit getCombatUnit() {
        return combatUnit;
    }

    @Override
    public Unit getNonCombatUnit() {
        return NonCombatUnit;
    }


    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public void setLooted(boolean looted) {
        isLooted = looted;
    }

    public void setNonCombatUnit(Unit nonCombatUnit) {
        NonCombatUnit = nonCombatUnit;
    }

    public void setCombatUnit(Unit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getGoldRate() {
        return goldRate;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public int getMovingCost() {
        int movingCost = this.movingCost;
        if (hasRoad) movingCost -= movingCost/2;
        if (hasRail) movingCost -= movingCost/3;
        return movingCost;
    }

    public int getImpactOnWar() {
        return impactOnWar;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setGoldRate(int goldRate) {
        this.goldRate = goldRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public void setMovingCost(int movingCast) {
        this.movingCost = movingCast;
    }

    public void setImpactOnWar(int impactOnWar) {
        this.impactOnWar = impactOnWar;
    }

    public boolean isUsableResource() {
        return isUsableResource;
    }

    public void setUsableResource(boolean usableResource) {
        isUsableResource = usableResource;
    }
}
