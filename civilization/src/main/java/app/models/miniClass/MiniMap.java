package app.models.miniClass;

import app.models.map.Map;

public class MiniMap {
    protected int mapWidth;
    protected int mapHeight;

    public MiniMap(Map map) {
        this.mapWidth = map.getMapWidth();
        this.mapHeight = map.getMapHeight();
    }
}
