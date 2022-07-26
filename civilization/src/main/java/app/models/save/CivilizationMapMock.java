package app.models.save;

import app.controllers.singletonController.GSave;
import app.models.map.CivilizationMap;
import app.models.tile.VisibleTile;

import java.util.ArrayList;

public class CivilizationMapMock extends Mock {

    private ArrayList<ArrayList<Integer>> mapID  = new ArrayList<>();
    private ArrayList<Integer> transparentTilesID = new ArrayList<>();

    public CivilizationMapMock(CivilizationMap personalMap, Integer id) {
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

    public CivilizationMapMock() {
        super(0);
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
