//besmellah
package app.models.map;

import app.models.tile.VisibleTile;

import java.util.ArrayList;

public class CivilizationMap extends Map {
    private ArrayList<ArrayList<VisibleTile>> map  = new ArrayList<>();
    private ArrayList<VisibleTile> transparentTiles = new ArrayList<>();

    public VisibleTile getTileByXY(int x, int y){
        System.out.println(mapHeight + "---" + mapWidth);
        System.out.println(x + "---" + y);
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;
        return map.get(x).get(y);
    }

    public boolean isTransparent(VisibleTile tile) {
        for (VisibleTile transparentTile : transparentTiles) {
            if (transparentTile.getX() == tile.getX() && transparentTile.getY() == tile.getY()) return true;
        }
        return false;
    }

    public ArrayList<ArrayList<VisibleTile>> getMap() {
        return map;
    }

    public ArrayList<VisibleTile> getTransparentTiles() {
        return transparentTiles;
    }
}