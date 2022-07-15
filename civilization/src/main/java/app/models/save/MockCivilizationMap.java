package app.models.save;

import app.controllers.GSave;
import app.models.map.CivilizationMap;
import app.models.tile.VisibleTile;

import java.util.ArrayList;

public class MockCivilizationMap extends Mock {

    private ArrayList<ArrayList<Integer>> mapID  = new ArrayList<>();
    private ArrayList<Integer> transparentTilesID = new ArrayList<>();

    public MockCivilizationMap(CivilizationMap personalMap, Integer id) {
        super(id);

        ArrayList<Integer> map1DID;
        for (ArrayList<VisibleTile> tileArrayList : personalMap.getMap()) {
            map1DID = new ArrayList<>();
            for (VisibleTile tile : tileArrayList)
                map1DID.add(GSave.getInstance().save(tile));
            mapID.add(map1DID);
        }
        personalMap.getTransparentTiles().forEach(tile -> transparentTilesID.add(GSave.getInstance().save(tile)));
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
