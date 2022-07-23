package app.models.miniClass;

import app.controllers.GLoad;
import app.models.map.CivilizationMap;
import app.models.miniClass.tile.MiniVisibleTile;
import app.models.tile.VisibleTile;

import java.util.ArrayList;

public class MiniCivilizationMap extends MiniMap{
    private ArrayList<ArrayList<Integer>> map; //TODO at the first of each turn, update civilization map.
    private ArrayList<Integer> tT = new ArrayList<>();

    public MiniCivilizationMap() {
        super();
    }

    @Override
    public CivilizationMap getOriginal() {
        CivilizationMap civilizationMap = new CivilizationMap();

        for (ArrayList<Integer> integers : this.map) {
            ArrayList<VisibleTile> visibleTiles = new ArrayList<>();
            for (Integer integer : integers) {
                visibleTiles.add((VisibleTile) GLoad.getInstance().load(new MiniVisibleTile(), integer));
            }
            civilizationMap.getMap().add(visibleTiles);
        }

        this.tT.forEach(id -> civilizationMap.getTransparentTiles().add((VisibleTile)GLoad.getInstance().load(new MiniVisibleTile(), id)));

        return civilizationMap;
    }
}
