package models.map;

import models.tile.AbstractTile;

public abstract class Map {
    protected int mapWidth;
    protected int mapHeight;

    public Map() {
        //TODO
    }

    public Map(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public static int[] getS(int q, int r){
        return new int[]{q, r, -r - q};
    }

    public static int[] cubeToXY(int q, int r , int s){
        int y = r;
        int x = q + (r - (r&1))/2;
        return new int[]{x, y};
    }

    public static int[] XYToCube(int x, int y){
        int q = x - (y - (y&1))/2;
        int r = y;
        return new int[]{q,r,-q-r};
    }

    public abstract AbstractTile getTileByXY(int x, int y);
    public abstract AbstractTile getTileByCube(int q, int r, int s);

}