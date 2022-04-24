package models.tile;

import models.unit.NonCombatUnit;
import models.unit.CombatUnit;

import java.util.ArrayList;

public class Tile extends Property{

    public int x;
    public int y;
    public String terrainName;
    public String featureName;

    public String improvement;
    public ArrayList<String> resources;

    public boolean hasRoad;
    public boolean hasRail;
    public boolean isLooted;
    public ArrayList<Boolean> isRiver;

    public NonCombatUnit NonCombatUnit;
    public CombatUnit combatUnit;

    public Tile(String terrainName, String featureName, int x, int y) {
        super();
        //
        //todo calculate fields
        //
        this.terrainName = terrainName;
        this.featureName = featureName;
        this.x = x;
        this.y = y;
    }


    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public ArrayList<String> getResources() {
        return resources;
    }

    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }

    public boolean isHasRoad() {
        return hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public boolean isHasRail() {
        return hasRail;
    }

    public void setHasRail(boolean hasRail) {
        this.hasRail = hasRail;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public void setLooted(boolean looted) {
        isLooted = looted;
    }

    public NonCombatUnit getCombatUnit() {
        return NonCombatUnit;
    }

    public void setCombatUnit(NonCombatUnit combatUnit) {
        this.NonCombatUnit = combatUnit;
    }

    public void deleteCombatUnit() {
        this.NonCombatUnit = null;
    }

    public CombatUnit getNonCombatUnit() {
        return combatUnit;
    }

    public void setNonCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public void deleteNonCombatUnit() {
        this.combatUnit = null;
    }

    public ArrayList<Boolean> getIsRiver() {
        return isRiver;
    }

    public void setIsRiver(ArrayList<Boolean> isRiver) {
        this.isRiver = isRiver;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getTerrainName() {
        return terrainName;
    }

    public void setTerrainName(String terrainName) {
        this.terrainName = terrainName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setNonCombatUnit(models.unit.NonCombatUnit nonCombatUnit) {
        NonCombatUnit = nonCombatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }
}
