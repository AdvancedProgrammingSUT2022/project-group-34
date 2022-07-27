package app.controllers;

import app.models.map.GameMap;
import app.models.tile.Improvement;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;
import app.models.unit.*;

import java.util.ArrayList;

public class CivilizationController {
    private final GameMap gameMap;

    private static CivilizationController instance = null;

    private CivilizationController() {
        gameMap = GameController.getInstance().getGame().getMainGameMap();
    }

    public static CivilizationController getInstance() {
        if (instance == null) instance = new CivilizationController();
        return instance;
    }


    public Tile getTileByPosition(int[] position) {
        return gameMap.getTileByXY(position[0], position[1]);
    }


    public boolean isRiverBetween(Tile tile1, Tile tile2) {
        if (tile1 == null || tile2 == null) return false;

        if (!tile1.getAdjacentTiles().contains(tile2)) return false;
        return tile1.getIsRiver().get(tile1.getAdjacentTiles().indexOf(tile2));

    }

    public String moveUnit(Unit unit, int[] position) {
        return null;
    }

    public String garrisonCity(CombatUnit selectedCombatUnit) {
        return "";
    }

    public String foundCity(Settler selectedNonCombatUnit, String name) {
        return "";
    }

    public void deleteUnit(Unit unit) {

    }

    public ArrayList<String> getPossibleImprovements(Tile tile) {
        return null;
    }

    public void build(Worker selectedNonCombatUnit, String s) {

    }

    public String repair(NonCombatUnit selectedNonCombatUnit) {
        return "";
    }
}