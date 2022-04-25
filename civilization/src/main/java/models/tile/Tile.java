package models.tile;

import models.unit.NonCombatUnit;
import models.unit.CombatUnit;

import java.util.ArrayList;

public class Tile extends Property{

    public int x;
    public int y;
    public String terrainName;
    public String featureName;
    public String improvementName = null;

    public ArrayList<String> resources;

    public boolean hasRoad = false;
    public boolean hasRail = false;
    public boolean isLooted = false;
    public boolean isInFog = false;
    public boolean canSeeBeyondHeights = false;
    public ArrayList<Boolean> isRiver = new ArrayList<>();

    public NonCombatUnit NonCombatUnit = null;
    public CombatUnit combatUnit = null;

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

        this.foodRate       += terrain.foodRate;
        this.goldRate       += terrain.goldRate;
        this.productionRate += terrain.productionRate;
        this.impactOnWar    += terrain.impactOnWar;
        this.movingCost     += terrain.movingCost;
        this.canSeeBeyondHeights |= terrain.canSeeBeyondHeights;
    }

    private void setFeatureProperties(String featureName) {
        this.featureName = featureName;
        Feature feature = Feature.allFeatures.get(featureName);

        if(featureName.equals("Forest")){
            this.foodRate       = 1;
            this.goldRate       = 1;
        }
        else{
            this.foodRate       += feature.foodRate;
            this.goldRate       += feature.goldRate;
        }

        this.productionRate += feature.productionRate;
        this.impactOnWar    += feature.impactOnWar;

        if (feature.movingCost == -1 || this.movingCost == -1) this.movingCost = -1;
        else this.movingCost += feature.movingCost;

    }

    private void setImprovementProperties(String improvementName) {
        this.improvementName = improvementName;
        Improvement improvement = Improvement.allImprovements.get(improvementName);
        this.foodRate       += improvement.foodRate;
        this.goldRate       += improvement.goldRate;
        this.productionRate += improvement.productionRate;
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


}
