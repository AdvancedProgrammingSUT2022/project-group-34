package app.models.miniClass;

import app.controllers.GLoad;
import app.models.map.GameMap;
import app.models.miniClass.tile.MiniTile;
import app.models.tile.Tile;

import java.util.ArrayList;

public class MiniGameMap extends MiniMap {

    private ArrayList<ArrayList<Integer>> map = new ArrayList<>();


    public MiniGameMap() {
        super();

    }

    @Override
    public Object getOriginal() {
        GameMap gameMap = new GameMap(this.mapWidth,this.mapHeight);

        for (ArrayList<Integer> integers : map) {
            ArrayList<Tile> tiles = new ArrayList<>();
            for (Integer id : integers) {
                tiles.add((Tile) GLoad.getInstance().load(new MiniTile(), id));
            }
            gameMap.getMap().add(tiles);
        }
        return gameMap;
    }
}
