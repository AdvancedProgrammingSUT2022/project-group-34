//besmellah

package controllers;

import models.unit.Settler;
import models.unit.Unit;
import models.unit.CombatUnit;
import models.unit.NonCombatUnit;
import models.City;
import models.tile.Tile;
import models.Technology;

public class CivilizationController {
    private static CivilizationController instance = null;

    private CivilizationController() {
    }

    public static CivilizationController getInstance() {
        if (instance == null) instance = new CivilizationController();
        return instance;
    }

    public CombatUnit getCombatUnitByPosition(int[] position) {
        //TODO
        return null;
    }

    public NonCombatUnit getNonCombatUnitByPosition(int[] position) {
        //TODO
        return null;
    }

    public City getCityByPosition(int[] position) {
        //TODO
        return null;
    }

    public Tile getTileByPosition(int[] position) {
        //TODO
        return null;
    }

    public boolean isPositionValid(int[] position) {
        //TODO
        return false;
    }


    public String moveUnit(Unit unit, int[] destination) {
        //TODO
        String ans = isMoveValid(unit, destination);
        if (!ans.equals("true")) return ans;
        Tile originTile = unit.getPosition();
        Tile destinationTile = getTileByPosition(destination);
        return "success";
    }

    private String isMoveValid(Unit unit, int[] destination) {
        Tile destinationTile = getTileByPosition(destination);
        if (!isPositionValid(destination)) return "invalid destination";
        else if (GameController.getInstance().getCivilization().isFogOfWar(destinationTile)) return "fog of war";
        else if (unit.getPosition() == destinationTile) return "already at the same tile";
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) return "destination occupied";
        else if (unit instanceof NonCombatUnit && destinationTile.getNonCombatUnit() != null)
            return "destination occupied";
        return "true";
    }


    public void makeUnitSleep(Unit unit) {
        //TODO
    }

    public void alertUnit(Unit unit) {
        //TODO
    }

    public void fortifyUnit(Unit unit) {
        //TODO
    }

    public void fullyFortifyUnit(Unit unit) {
        //TODO
    }

    public void garrisonUnit(Unit unit) {
        //TODO
    }

    public void setupUnit(Unit unit) {
        //TODO
    }


    public void foundCity(Settler settler) {
        //TODO
    }

    public void cancelMission(Unit unit) {
        //TODO
    }

    public void wakeUnit(Unit unit) {
        //TODO
    }

    public void removeUnit(Unit unit) {
        //TODO
    }

    public void build(int[] position, String improvement) {
        //TODO
    }

    public void removeItem(int[] position, String item) {
        //TODO
    }

    public void repair(int[] position) {
        //TODO
    }


    public void cityFortify(City city) {
        //TODO
    }

    public void purchaseTile(City city, int[] position) {
        //TODO
    }

    public void chooseCityProduction(City city, int index) {
        //TODO
    }

    public void updateCivilizationAttributes() {
        //TODO
    }

    public void research(Technology technology) {
        //TODO
    }


}