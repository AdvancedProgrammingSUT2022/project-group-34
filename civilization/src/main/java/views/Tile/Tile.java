package views.Tile;

import views.Unit.NonCombatUnit;
import views.Unit.CombatUnit;

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



}
