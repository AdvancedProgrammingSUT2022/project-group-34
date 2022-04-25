package models.tile;

public class Property {

    private String name;
    private int foodRate;
    private int goldRate;
    private int productionRate;
    private int movingCost;
    private int impactOnWar;

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

    public int getMovingCost() {
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

    public void setMovingCost(int movingCast) {
        this.movingCost = movingCast;
    }

    public void setImpactOnWar(int impactOnWar) {
        this.impactOnWar = impactOnWar;
    }
}
