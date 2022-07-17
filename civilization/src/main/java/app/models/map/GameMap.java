package app.models.map;

import app.models.resource.ResourcesLocationsDataSheet;
import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.Tile;
import app.models.resource.Resource;
import app.models.resource.ResourceEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameMap extends Map {

    private ArrayList<ArrayList<Tile>> map = new ArrayList<>();

    public GameMap() {
        super();
        // TODO
    }

    public GameMap(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    static public GameMap load() {
        GameMap answer = new GameMap();
        return null;
        // TODO: make a map in GSon and save it, load it here.
        // The template of the file (saving map) :
        // map height
        // map width
        // a JSon array for each argument of Tile constructor
    }

    public Tile getTileByXY(int x, int y){
        if(x < 0 || this.mapHeight <= x || y < 0 || this.mapWidth <= y)
            return null;
        return map.get(x).get(y);
    }

    public Tile getTileByCube(int q, int r, int s){
        int[] xy = cubeToXY(q,r,s);
        int x = xy[0];
        int y = xy[1];
        //System.out.print(" " + x + "*" + y + " ");
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

        for (int i = 0; i < 6; i++) {
            Tile tile1 = getAdjacentTileByNumber(tile, i);
            tiles.add(tile1);
        }

        tiles.removeAll(Collections.singleton(null));
        return tiles;
    }

    public Tile getAdjacentTileByNumber(Tile tile, int number){
        int x = tile.getX();
        int y = tile.getY();

        //TODO : remove this
        //System.out.print(" " + x + "*" + y + " -> ");
        int[] qrs = XYToCube(x,y);
        int q = qrs[0];
        int r = qrs[1];
        int s = qrs[2];
        int[] xy = cubeToXY(q,r,s);
        //TODO : remove this
        //System.out.print(" " + xy[0] + "*" + xy[1] + " -> ");

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
        //System.out.println();
        return null;
    }

    public ArrayList<Boolean> getIsRiver(Tile tile){
        return tile.getIsRiver();
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

    public void generateMap(){
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0 ; i < mapHeight; i++){
            map.add(new ArrayList<>());
            for (int j = 0; j < mapWidth; j++) {
                Terrain terrain = generateTerrain(i, j, random);
                Tile tile = new Tile(terrain, null, i, j, null, null);

                map.get(i).add(tile);

            }
        }

        for (int i = 0 ; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++) {

                Tile tile = map.get(i).get(j);
                boolean hasRiver = generateRivers(tile, random);
                Feature feature = generateFeature(i, j, tile.getTerrain(), hasRiver, random);
                tile.setFeature(feature);
            }
        }

        for (int i = 0 ; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++) {

                Tile tile = map.get(i).get(j);
                Resource resource = getRandomResourceForTile(tile,random);
                //System.out.println(resource);
                tile.setResource(resource);
            }
        }



        for (int i = 0 ; i < mapHeight ; i++) {
            for (int j = 0; j < mapWidth; j++) {
                Tile tile = map.get(i).get(j);
                tile.setAdjacentTiles(getAdjacentTiles(tile));

            }
        }
    }

    private Terrain generateTerrain(int x, int y, Random random) {
        if (x <= 2 || y <= 2 || x + 3 >= mapHeight || y + 3 >= mapWidth) return Terrain.Ocean;
        int desertProbability = Math.min(x, mapHeight - x) * 2;
        int snowProbability = (55 - desertProbability) / 2;
        int tundraProbability = 55 - desertProbability - snowProbability;
        int grasslandsProbability = 20;
        int hillsProbability = 10;
        int mountainProbability = 5;
        int plainProbability = 20;
        int randomNumber = random.nextInt(100);
        if (randomNumber < desertProbability) return Terrain.Desert;
        randomNumber -= desertProbability;
        if (randomNumber < snowProbability) return Terrain.Snow;
        randomNumber -= snowProbability;
        if (randomNumber < tundraProbability) return Terrain.Tundra;
        randomNumber -= tundraProbability;
        if (randomNumber < grasslandsProbability) return Terrain.Grasslands;
        randomNumber -= grasslandsProbability;
        if (randomNumber < hillsProbability) return Terrain.Hills;
        randomNumber -= hillsProbability;
        if (randomNumber < mountainProbability) return Terrain.Mountain;
        return Terrain.Plains;
    }

    private Feature generateFeature(int x, int y, Terrain terrain, boolean hasRiver, Random random) {
        int randomNumber = random.nextInt(100);
        if (terrain.equals(Terrain.Desert)) {
            if (randomNumber < 20) return Feature.Oasis;
            if (hasRiver && randomNumber < 70) return Feature.FloodPlain;
        }
        else if (terrain.equals(Terrain.Grasslands)) {
            if (randomNumber < 30) return Feature.Forests;
            if (randomNumber < 50) return Feature.Marsh;
        }
        else if (terrain.equals(Terrain.Hills)) {
            if (randomNumber < 30) return Feature.Forests;
            if (randomNumber < 50) return Feature.Jungle;
        }
        else if (terrain.equals(Terrain.Plains)) {
            if (randomNumber < 30) return Feature.Forests;
            if (randomNumber < 50) return Feature.Jungle;
        }
        else if (terrain.equals(Terrain.Tundra)) {
            if (randomNumber < 30) return Feature.Forests;
        }
        return null;
    }

    private boolean generateRivers(Tile tile, Random random) {
        if (tile.getTerrain().equals(Terrain.Ocean)) return false;
        boolean answer = false;
        for (int i = 0; i < 6; i++) {
            if (random.nextInt(100) > 10) continue;
            tile.addRiver(i);
            Tile adjacentTile = getAdjacentTileByNumber(tile, i);
            if (adjacentTile != null) adjacentTile.addRiver((3 + i) % 6);
            answer = true;
        }
        return answer;
    }

    public static Resource getRandomResourceForTile(Tile tile, Random random){
        int n = random.nextInt();
        if (n < 0) n = -n;
        ArrayList<ResourceEnum> resourceEnums;
        resourceEnums = ResourcesLocationsDataSheet.whichResourcesInThisTile(tile);
        Resource resource = null;
        if (resourceEnums.size() != 0){
            ResourceEnum resourceEnum = resourceEnums.get(n % resourceEnums.size());
            resource = Resource.getAllResourcesCopy().get(resourceEnum);
        }
        return resource;
    }

    public ArrayList<ArrayList<Tile>> getMap() {
        return map;
    }

    public void setMap(ArrayList<ArrayList<Tile>> map2D) {
        this.map = map2D;
    }
}
