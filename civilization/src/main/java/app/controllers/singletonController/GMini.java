package app.controllers.singletonController;

import app.controllers.gameServer.GameController;
import app.models.Civilization;
import app.models.Game;
import app.models.connection.StringGameToken;
import app.models.map.CivilizationMap;
import app.models.map.GameMap;
import app.models.map.Map;
import app.models.miniClass.*;
import app.models.miniClass.tile.MiniTile;
import app.models.miniClass.tile.MiniVisibleTile;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import app.models.unit.Unit;
import com.google.gson.Gson;

import java.util.HashMap;

public class GMini {

    private static StringGameToken stringGameToken;
    private Integer uniqueTempID;
    private HashMap<Integer, String> temp = new HashMap<>();
    private HashMap<Object, Integer> allTempObject = new HashMap<>();

    private static GMini instance;

    private GMini(){}

    public static GMini getInstance() {
        if (instance == null) instance = new GMini();
        return instance;
    }

    public static StringGameToken getGameToken() {
        return stringGameToken;
    }

    public Integer miniSave(Object object) {
        if (object == null) return null;
        Integer id;
        if ((id = allTempObject.get(object)) != null) return id;
        id = getUniqueTempID();
        allTempObject.put(object,id);
        temp.put(id, new Gson().toJson(getMiniObject(object)));
        return id;
    }

    public Object getMiniObject(Object object){
        if (object instanceof CivilizationMap)         return new MiniCivilizationMap((CivilizationMap) object);
        else if (object instanceof Civilization)        return new MiniCivilization((Civilization) object);
        else if (object instanceof GameController)      return new MiniGameController((GameController) object);
        else if (object instanceof Game)                return new MiniGame((Game) object);
        else if (object instanceof GameMap)             return new MiniGameMap((GameMap) object);
        else if (object instanceof Map)                 return new MiniMap((Map) object);
        else if (object instanceof Unit)                return new MiniUnit((Unit) object);
        else if (object instanceof Tile)                return new MiniTile((Tile) object);
        else if (object instanceof VisibleTile)         return new MiniVisibleTile((VisibleTile) object);
        else return null;
    }

    public String[] startMiniSave(Object object, StringGameToken token){
        stringGameToken = token;
        uniqueTempID = 0;
        allTempObject.clear();
        temp.clear();
        int idd = miniSave(object);
        System.out.println("TEMP : " + temp.size() + " " + idd);
        System.out.println(temp.get(0));
        String[] tempStr = new String[temp.size()];
        temp.forEach((i, json) -> tempStr[i] = json);
        return tempStr;
    }

    private Integer getUniqueTempID() {
        uniqueTempID++;
        return uniqueTempID - 1;
    }
}
