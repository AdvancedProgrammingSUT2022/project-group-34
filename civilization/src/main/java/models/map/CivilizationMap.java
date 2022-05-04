//besmellah
package models.map;

import models.tile.Tile;
import models.tile.VisibleTile;

import java.util.ArrayList;

public class CivilizationMap extends Map {
    private ArrayList<ArrayList<VisibleTile>> map;

    public CivilizationMap(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    public VisibleTile getTileByXY(int x, int y){
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;
        return map.get(x).get(y);
    }

    public VisibleTile getTileByCube(int q, int r, int s){
        int[] xy = cubeToXY(r,s,q);
        int x = xy[0];
        int y = xy[1];

        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;

        return map.get(x).get(y);
    }
}