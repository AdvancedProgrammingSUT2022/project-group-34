package app.models.miniClass;

import app.controllers.GMini;
import app.models.map.CivilizationMap;
import app.models.tile.VisibleTile;

import java.util.ArrayList;

public class MiniCivilizationMap extends MiniMap{
    private ArrayList<ArrayList<Integer>> map; //TODO at the first of each turn, update civilization map.
    private ArrayList<Integer> tT = new ArrayList<>();

    public MiniCivilizationMap(CivilizationMap civilizationMap) {
        super(civilizationMap);
        map = new ArrayList<>();
        for (int i = 0; i < mapHeight; i++) {
            map.add(new ArrayList<>());
            for (int j = 0; j < mapWidth; j++) {
                VisibleTile tile = civilizationMap.getMap().get(i).get(j);
                map.get(i).add(GMini.getInstance().miniSave(tile));
            }
        }
        civilizationMap.getTransparentTiles().forEach(visibleTile -> this.tT.add(GMini.getInstance().miniSave(visibleTile)));
    }

}
