package models.tile;

public class Property {

    protected String name;
    protected int foodRate;
    protected int goldRate;
    protected int productionRate;
    protected int movingCost;
    protected int impactOnWar;

    public String getName() {
        return name;
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

    public int getMovingCast() {
        return movingCost;
    }

    public int getImpactOnWar() {
        return impactOnWar;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setMovingCast(int movingCast) {
        this.movingCost = movingCast;
    }

    public void setImpactOnWar(int impactOnWar) {
        this.impactOnWar = impactOnWar;
    }
}
