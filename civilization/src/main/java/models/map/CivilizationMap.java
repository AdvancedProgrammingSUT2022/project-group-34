//besmellah
package models.map;

import models.tile.AbstractTile;
import models.tile.Tile;
import models.tile.VisibleTile;

import java.util.ArrayList;

public class CivilizationMap extends Map {
    private ArrayList<ArrayList<VisibleTile>> map; //TODO at the first of each turn, update civilization map.
    private ArrayList<VisibleTile> transparentTiles; //TODO at the start of each turn, determine this ArrayList.

    public CivilizationMap(int mapWidth, int mapHeight, GameMap mainMap) {
        super(mapWidth, mapHeight);
        map = new ArrayList<>();
        for (int i = 0; i < mapHeight; i++) {
            map.add(new ArrayList<>());
            for (int j = 0; j < mapWidth; j++) {
                map.get(i).add(new VisibleTile(mainMap.getTileByXY(i, j), true));
            }
        }
    }

    public VisibleTile getTileByXY(int x, int y){
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;
        return map.get(x).get(y);
    }

    public void setTileByXY(int x, int y, VisibleTile visibleTile) {
        map.get(x).set(y, visibleTile);
    }

    public VisibleTile getTileByCube(int q, int r, int s){
        int[] xy = cubeToXY(r,s,q);
        int x = xy[0];
        int y = xy[1];

        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;

        return map.get(x).get(y);
    }

    public boolean isTransparent(VisibleTile tile) {
        return transparentTiles.contains(tile);
    }

    public void removeTransparentTiles() {
        transparentTiles = new ArrayList<>();
    }

    public void addTransparentTiles(ArrayList<AbstractTile> tiles) {
        for (AbstractTile tile : tiles) {
            transparentTiles.add(getTileByXY(tile.getX(), tile.getY()));
        }
    }
}