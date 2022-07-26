package app.models.miniClass;

import app.controllers.singletonController.GMini;
import app.models.map.GameMap;
import app.models.tile.Tile;

import java.util.ArrayList;

public class MiniGameMap extends MiniMap{

    private ArrayList<ArrayList<Integer>> map;

    public MiniGameMap(GameMap gameMap) {
        super(gameMap);
        this.map = new ArrayList<>();;
        for (ArrayList<Tile> tilesmapOrg : gameMap.getMap()) {
            ArrayList<Integer> copy = new ArrayList<>();
            for (Tile tile : tilesmapOrg)
                copy.add(GMini.getInstance().miniSave(tile));
            map.add(copy);
        }
    }
}
