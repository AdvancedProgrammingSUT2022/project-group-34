package models.tile;

import models.unit.CombatUnit;
import models.unit.NonCombatUnit;

import java.util.ArrayList;

public class PseudoTile {

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

    public models.unit.NonCombatUnit NonCombatUnit = null;
    public CombatUnit combatUnit = null;

    public PseudoTile(String terrainName, String featureName, int x, int y) {

    }


    private void setTerrainProperties(String terrainName) {

        if (terrainName == null) return;

        this.terrainName = terrainName;
        Terrain terrain = Terrain.allTerrains.get(terrainName);

        this.canSeeBeyondHeights |= terrain.canSeeBeyondHeights;
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

}
