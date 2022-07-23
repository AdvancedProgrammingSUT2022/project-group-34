package app.models.map;

import app.models.tile.AbstractTile;

public abstract class Map {
    protected int mapWidth;
    protected int mapHeight;

    public Map() {
        //TODO
    }

    public abstract AbstractTile getTileByXY(int x, int y);

}