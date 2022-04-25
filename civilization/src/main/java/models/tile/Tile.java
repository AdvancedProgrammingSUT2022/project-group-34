package models.tile;

import models.City;
import models.unit.NonCombatUnit;
import models.unit.CombatUnit;

import java.util.ArrayList;

public class Tile extends Property{

    private int x;
    private int y;
    private String terrainName;
    private String featureName;
    private String improvementName = null;
    private ArrayList<Tile> adjacentTiles;
    private City city;

    private ArrayList<String> resources;

    private boolean hasRoad = false;
    private boolean hasRail = false;
    private boolean isLooted = false;
    private boolean isInFog = false;
    private boolean canSeeBeyondHeights = false;
    private ArrayList<Boolean> isRiver = new ArrayList<>();

    private NonCombatUnit NonCombatUnit = null;
    private CombatUnit combatUnit = null;

    public Tile(String terrainName, String featureName, int x, int y) {
        super();
        this.terrainName = terrainName;
        this.featureName = featureName;
        this.x = x;
        this.y = y;

        for (int i = 0; i < 6; i++) isRiver.add(false);
        setTileProperties();
    }

    public void setTileProperties(){
        setTerrainProperties(this.terrainName);
        setFeatureProperties(this.featureName);
        setImprovementProperties(this.improvementName);
    }

    private void setTerrainProperties(String terrainName) {

        if (terrainName == null) return;

        this.terrainName = terrainName;
        Terrain terrain = Terrain.allTerrains.get(terrainName);

        this.setFoodRate        (this.getFoodRate() + terrain.getFoodRate());
        this.setGoldRate        (this.getGoldRate() + terrain.getGoldRate());
        this.setProductionRate  (this.getProductionRate() + terrain.getProductionRate());
        this.setImpactOnWar     (this.getImpactOnWar() + terrain.getImpactOnWar());
        this.setMovingCost      (this.getMovingCost() + terrain.getMovingCost());
        this.canSeeBeyondHeights |= terrain.canSeeBeyondHeights;
    }

    private void setFeatureProperties(String featureName) {
        this.featureName = featureName;
        Feature feature = Feature.allFeatures.get(featureName);

        if(featureName.equals("Forest")){
            this.setFoodRate(1);
            this.setGoldRate(1);
        }
        else{
            this.setFoodRate(this.getFoodRate() + feature.getFoodRate());
            this.setGoldRate(this.getFoodRate() + feature.getGoldRate());
        }

        this.setProductionRate(this.getProductionRate() + feature.getProductionRate());
        this.setImpactOnWar(this.getImpactOnWar() + feature.getImpactOnWar());

        if (feature.getMovingCost() == -1 || this.getMovingCost() == -1) this.setMovingCost(-1);
        else this.setMovingCost(this.getMovingCost() + feature.getMovingCost());

    }

    private void setImprovementProperties(String improvementName) {
        this.improvementName = improvementName;
        Improvement improvement = Improvement.allImprovements.get(improvementName);
        this.setFoodRate(this.getFoodRate() + improvement.getFoodRate());
        this.setGoldRate(this.getGoldRate() + improvement.getGoldRate());
        this.setProductionRate(this.getProductionRate() + improvement.getProductionRate());
    }



    public void deleteTerrainName() {
        this.terrainName = null;
        setTileProperties();
    }

    public void deleteFeatureName() {
        this.featureName = null;
        if (featureName.equals("Jungle"))
            this.terrainName = "Plain";

        setTileProperties();
    }

    public void deleteImprovementName() {
        this.featureName = null;
        if (featureName.equals("Jungle"))
            this.terrainName = "Plain";

        setTileProperties();
    }




    public ArrayList<String> getResources() {
        return resources;
    }

    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
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

    public void setCanSeeBeyondHeights(boolean canSeeBeyondHeights) {
        this.canSeeBeyondHeights = canSeeBeyondHeights;
    }

    public boolean isHasRoad() {
        return hasRoad;
    }

    public boolean isHasRail() {
        return hasRail;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public boolean isInFog() {
        return isInFog;
    }

    public boolean isCanSeeBeyondHeights() {
        return canSeeBeyondHeights;
    }






    public NonCombatUnit getCombatUnit() {
        return NonCombatUnit;
    }

    public CombatUnit getNonCombatUnit() {
        return combatUnit;
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


    public void setTerrainName(String terrainName) {
        this.terrainName = terrainName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setImprovementName(String improvementName) {
        this.improvementName = improvementName;
    }

    public String getTerrainName() {
        return terrainName;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public String getImprovementName() {
        return improvementName;
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


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }
}
