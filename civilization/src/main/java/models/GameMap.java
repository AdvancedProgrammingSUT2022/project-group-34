package models;

import models.tile.Tile;

import java.util.ArrayList;

public class GameMap {

    private ArrayList<ArrayList<Tile>> map;
    private int mapWidth;
    private int mapHeight;

    public GameMap(int mapWidth, int mapHeight) {
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

        tiles.add(getTileByCube(q+1,r+0,s-1));
        tiles.add(getTileByCube(q+0,r+1,s-1));
        tiles.add(getTileByCube(q-1,r+1,s+0));
        tiles.add(getTileByCube(q-1,r+0,s+1));
        tiles.add(getTileByCube(q+0,r-1,s+1));
        tiles.add(getTileByCube(q+1,r-1,s+0));

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
        for (int i = 0 ; i < mapHeight ; i++){
            map.add(new ArrayList<>());
            for (int j = 0; j < mapWidth; j++) {
                map.get(i).add(new Tile("Plain" , "Jungle" , i , j));
                Tile tile = map.get(i).get(j);
                if (i % 50 == 0 && j % 23 == 0){
                    tile.addRiver(j%6);
                    getAdjacentTileByNumber(tile,j%6).addRiver((3 + j%6) % 6);
                }
            }
        }
    }
}
