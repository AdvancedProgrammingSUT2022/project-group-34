package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
import app.models.map.GameMap;
import app.models.tile.Tile;

import java.util.ArrayList;

public class GameMapMock extends Mock{

    private ArrayList<ArrayList<Integer>> mapID = new ArrayList<>();
    protected int mapWidth;
    protected int mapHeight;

    public GameMapMock(GameMap gameMap, Integer id) {
        super(id);

        ArrayList<Integer> map1DID;
        for (ArrayList<Tile> tileArrayList : gameMap.getMap()) {
            map1DID = new ArrayList<>();
            for (Tile tile : tileArrayList)
                map1DID.add(GSave.getInstance().save(tile));
            mapID.add(map1DID);
        }

        this.mapWidth = gameMap.getMapWidth();
        this.mapHeight = gameMap.getMapHeight();
    }

    public GameMapMock() {
        super(0);
    }


    @Override
    public GameMap getOriginalObject() {
        GameMap gameMap = new GameMap();

        ArrayList<ArrayList<Tile>> map2D = new ArrayList<>();
        ArrayList<Tile> map1D;
        for (ArrayList<Integer> idArrayList : this.mapID) {
            map1D = new ArrayList<>();
            for (Integer integer : idArrayList)
                map1D.add((Tile) GLoad.getInstance().load(new TileMock(),integer));
            map2D.add(map1D);
        }
        gameMap.setMap(map2D);
        gameMap.setMapHeight(this.mapHeight);
        gameMap.setMapWidth(this.mapWidth);

        return gameMap;
    }
}
