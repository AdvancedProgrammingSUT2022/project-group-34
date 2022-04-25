package views.Tile;

import views.Unit.CombatUnit;
import views.Unit.NonCombatUnit;

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

    public CombatUnit combatUnit;
    public NonCombatUnit nonCombatUnit;

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

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public void deleteCombatUnit() {
        this.combatUnit = null;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public void deleteNonCombatUnit() {
        this.nonCombatUnit = null;
    }



}
