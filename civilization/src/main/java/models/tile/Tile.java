package models.tile;

import models.City;
import models.resource.Resource;
import models.unit.NonCombatUnit;
import models.unit.CombatUnit;

import java.util.ArrayList;

public class Tile extends AbstractTile{

    private int x;
    private int y;
    private Terrain terrain;
    private Feature feature;
    private Improvement improvement = null;
    private Resource resource;
    private City city;

    private String name;
    int foodRate;
    int goldRate;
    int productionRate;
    int movingCost;
    int impactOnWar;
    private boolean hasRoad = false;
    private boolean hasRail = false;
    private boolean isLooted = false;
    private boolean isInFog = false;
    boolean isBlock = false;
    private ArrayList<Tile> adjacentTiles = new ArrayList<>();
    private ArrayList<Boolean> isRiver = new ArrayList<>();

    private NonCombatUnit NonCombatUnit = null;
    private CombatUnit combatUnit = null;

    public Tile(Terrain terrain, Feature feature, int x, int y, City city) {
        super();
        this.terrain = terrain;
        this.feature = feature;
        this.x = x;
        this.y = y;
        this.city = city;

        for (int i = 0; i < 6; i++) isRiver.add(false);
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





    public void deleteTerrainName() {
        this.terrain = null;
        setTileProperties();
    }

    public void deleteFeature() {
        if (feature.name.equals("Jungle"))
            this.terrain = Terrain.Plain;
        this.feature = null;
        setTileProperties();
    }

    public void deleteImprovement() {
        this.improvement = null;
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

    public void setInFog(boolean inFog) {
        isInFog = inFog;
    }

    public void setBlock(boolean block) {
        this.isBlock = block;
    }

    public boolean isHasRoad() {
        return hasRoad;
    }

    public boolean HasRail() {
        return hasRail;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public boolean isInFog() {
        return isInFog;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return NonCombatUnit;
    }

    public void setCombatUnit(NonCombatUnit combatUnit) {
        this.NonCombatUnit = combatUnit;
    }

    public void setNonCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public void deleteCombatUnit() {
        this.NonCombatUnit = null;
    }

    public void deleteNonCombatUnit() {
        this.combatUnit = null;
    }






    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }


    public Terrain getTerrain() {
        return terrain;
    }

    public Feature getFeature() {
        return this.feature;
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

    public ArrayList<Boolean> getIsRiver() {
        return isRiver;
    }

    public void addRiver(int index) {
        this.isRiver.set(index,true);
    }


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

    public boolean isUnmovable(){
        return (movingCost == -1);
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

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
