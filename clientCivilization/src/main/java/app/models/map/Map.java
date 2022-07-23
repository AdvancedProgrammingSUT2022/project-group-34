package app.models.map;

import app.models.tile.AbstractTile;

public abstract class Map {
    protected int mapWidth;
    protected int mapHeight;

    public Map() {
        //TODO
    }

    public abstract AbstractTile getTileByXY(int x, int y);

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}