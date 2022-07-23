package app.controllers;

import app.models.map.GameMap;
import app.models.tile.Tile;

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

}