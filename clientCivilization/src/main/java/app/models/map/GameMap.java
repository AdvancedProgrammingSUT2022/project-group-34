package app.models.map;

import app.models.tile.Tile;

import java.util.ArrayList;

public class GameMap extends Map {

    private final ArrayList<ArrayList<Tile>> map = new ArrayList<>();

    public GameMap(int mapWidth, int mapHeight) {
        super();
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    public Tile getTileByXY(int x, int y){
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y) {
            return null;
        }
        return map.get(x).get(y);
    }

    public ArrayList<ArrayList<Tile>> getMap() {
        return map;
    }
}
