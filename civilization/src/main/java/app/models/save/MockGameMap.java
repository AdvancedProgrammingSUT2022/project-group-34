package app.models.save;

import app.controllers.GSave;
import app.models.map.GameMap;
import app.models.tile.Tile;

import java.util.ArrayList;

public class MockGameMap extends Mock{

    private Integer id;

    private ArrayList<ArrayList<Integer>> mapID = new ArrayList<>();

    public MockGameMap(GameMap gameMap, Integer id) {
        super(id);

        ArrayList<Integer> map1DID;
        for (ArrayList<Tile> tileArrayList : gameMap.getMap()) {
            map1DID = new ArrayList<>();
            for (Tile tile : tileArrayList)
                map1DID.add(GSave.getInstance().save(tile));
            mapID.add(map1DID);
        }
    }


    @Override
    public Object getOriginalObject() {
        return null;
    }
}
