package app.models.miniClass;

import app.models.map.GameMap;
import app.models.map.Map;

public abstract class MiniMap extends Mini{
    protected int mapWidth;
    protected int mapHeight;
    
    public MiniMap(){
    }

    protected void cloneMap(Map map) {
        map.setMapHeight(mapHeight);
        map.setMapWidth(mapWidth);
    }
}
