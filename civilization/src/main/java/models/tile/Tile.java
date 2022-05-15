package models.tile;

import models.City;
import models.Civilization;
import models.resource.Resource;
import models.unit.NonCombatUnit;
import models.unit.CombatUnit;

import java.util.ArrayList;

public class Tile extends AbstractTile{

    private Improvement improvement = null;
    private Resource resource;

    int foodRate;
    int goldRate;
    int productionRate;
    int impactOnWar;
    private boolean hasRoad = false;
    private boolean hasRail = false;
    private boolean isLooted = false;

    private NonCombatUnit NonCombatUnit = null;
    private CombatUnit combatUnit = null;

    public Tile(Terrain terrain, Feature feature, int x, int y, City city, Civilization civilization) {
        super(terrain, feature, x, y, city, civilization);
        setTileProperties();
    }

    private void setInit(){
        foodRate = 0;
        goldRate = 0;
        productionRate = 0;
        movingCost = 0;
        impactOnWar = 0;
        isBlock = false;
    }

    public void setTileProperties(){

        setInit();
        Terrain     .setTerrainProperties(this,this.terrain);
        Feature     .setFeatureProperties(this,this.feature);
        int flag = Improvement .setImprovementProperties(this,this.improvement);
        if (flag == 1) {
            foodRate += this.resource.getFoodBonus();
            goldRate += this.resource.getGoldBonus();
            productionRate += this.resource.getProductionBonus();
        }
    }


    public void deleteFeature() {
        if (feature.name.equals("Jungle"))
            this.terrain = Terrain.Plains;
        this.feature = null;
        setTileProperties();
    }


    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
        setTileProperties();
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
        setTileProperties();
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
        setTileProperties();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResources(Resource resource) {
        this.resource = resource;
    }



    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public void setHasRail(boolean hasRail) {
        this.hasRail = hasRail;
    }

    public void setLooted(boolean looted) {
        isLooted = looted;
    }

    public void setBlock(boolean block) {
        this.isBlock = block;
    }

    public boolean HasRoad() {
        return hasRoad;
    }

    public boolean HasRail() {
        return hasRail;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public boolean isBlock() {
        return isBlock;
    }

    @Override
    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    @Override
    public NonCombatUnit getNonCombatUnit() {
        return NonCombatUnit;
    }


    public Improvement getImprovementName() {
        return improvement;
    }

    public void setNonCombatUnit(models.unit.NonCombatUnit nonCombatUnit) {
        NonCombatUnit = nonCombatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public void addRiver(int index) {
        this.isRiver.set(index,true);
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

    public boolean isUnmovable(){
        return (movingCost == -1);
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

    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
