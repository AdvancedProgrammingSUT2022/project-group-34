package models.map;

import models.Civilization;
import models.tile.Feature;
import models.tile.Terrain;
import models.tile.Tile;
import models.tile.VisibleTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameMap extends Map{

    private ArrayList<ArrayList<Tile>> map;

    public GameMap() {
        super();
        // TODO
    }

    static public GameMap load() {
        return null;
        // TODO: make a map in GSon and save it, load it here.
        // The template of the file (saving map) :
        // map height
        // map width
        // a JSon array for each argument of Tile constructor
    }

    public GameMap(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    public Tile getTileByXY(int x, int y){
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;
        return map.get(x).get(y);
    }

    public Tile getTileByCube(int q, int r, int s){
        int[] xy = cubeToXY(r,s,q);
        int x = xy[0];
        int y = xy[1];

        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;

        return map.get(x).get(y);
    }

    public ArrayList<Tile> getAdjacentTiles(Tile tile){
        int x = tile.getX();
        int y = tile.getY();
        int[] qrs = XYToCube(x,y);
        int q = qrs[0];
        int r = qrs[1];
        int s = qrs[2];

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 6; i++) tiles.add(getAdjacentTileByNumber(tile, i));

        tiles.removeAll(Collections.singleton(null));
        return tiles;
    }

    public Tile getAdjacentTileByNumber(Tile tile, int number){
        int x = tile.getX();
        int y = tile.getY();
        int[] qrs = XYToCube(x,y);
        int q = qrs[0];
        int r = qrs[1];
        int s = qrs[2];

        switch (number){
            case 0:
                return getTileByCube(q+1,r+0,s-1);
            case 1:
                return getTileByCube(q+0,r+1,s-1);
            case 2:
                return getTileByCube(q-1,r+1,s+0);
            case 3:
                return getTileByCube(q-1,r+0,s+1);
            case 4:
                return getTileByCube(q+0,r-1,s+1);
            case 5:
                return getTileByCube(q+1,r-1,s+0);
        }
        return null;
    }

    public ArrayList<Boolean> getIsRiver(Tile tile){
        return tile.getIsRiver();
    }


    public void GenerateGameMap(){

    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void createRandomMap(){

        HashMap<String,Terrain> allTerrains = Terrain.getAllTerrains();
        HashMap<String,Feature> allFeatures = Feature.getAllFeatures();
        Terrain terrain;
        Feature feature;
        Tile tile;

        map = new ArrayList<>();
        for (int i = 0 ; i < mapHeight ; i++){
            map.add(new ArrayList<>());
            for (int j = 0; j < mapWidth; j++) {

                terrain = allTerrains.get("Plain");
                feature = allFeatures.get("");
                tile = new Tile( terrain, feature , i , j, city);

                map.get(i).add(tile);

                if (i % 50 == 0 && j % 23 == 0){
                    tile.addRiver(j%6);
                    getAdjacentTileByNumber(tile,j%6).addRiver((3 + j%6) % 6);
                }
            }
        }

        Tile tile1;
        for (int i = 0 ; i < mapHeight ; i++)
            for (int j = 0; j < mapWidth; j++) {
                tile1 = map.get(i).get(j);
                tile1.setAdjacentTiles(getAdjacentTiles(tile1));
            }



    }

    public CivilizationMap clone() {
        //TODO
        CivilizationMap answer = new CivilizationMap(mapWidth, mapHeight);
        ArrayList<ArrayList<VisibleTile>> = new ArrayList<>();
        //TODO : clone every Tile
        for ();
        return null;
    }
}
