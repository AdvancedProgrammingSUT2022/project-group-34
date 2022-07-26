package app.controllers.singletonController;

import app.controllers.gameServer.GameController;
import app.models.*;
import app.models.map.CivilizationMap;
import app.models.map.GameMap;
import app.models.resource.Resource;
import app.models.resource.ResourceEnum;
import app.models.save.*;
import app.models.tile.Improvement;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import app.models.unit.Unit;
import app.models.unit.Work;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class GSave {

    private Integer uniqueID;
    private GameMock gameSave;


    private final HashMap<Technology,Integer> technologyIntegerHashMap = new HashMap<>();
    private final HashMap<Civilization,Integer> civilizationIntegerHashMap = new HashMap<>();
    private final HashMap<User,Integer> userIntegerHashMap = new HashMap<>();
    private final HashMap<GameMap,Integer> gameMapIntegerHashMap = new HashMap<>();
    private final HashMap<CivilizationMap,Integer> civilizationMapIntegerHashMap = new HashMap<>();
    private final HashMap<City,Integer> cityIntegerHashMap = new HashMap<>();
    private final HashMap<Work,Integer> workIntegerHashMap = new HashMap<>();
    private final HashMap<Notification,Integer> notificationIntegerHashMap = new HashMap<>();
    private final HashMap<Citizen, Integer> citizenIntegerHashMap = new HashMap<>();
    private final HashMap<VisibleTile, Integer> visibleTileIntegerHashMap = new HashMap<>();
    private final HashMap<Tile, Integer> tileIntegerHashMap = new HashMap<>();
    private final HashMap<Unit, Integer> unitIntegerHashMap = new HashMap<>();


    private static GSave gSave;

    private GSave(){
    }

    public static GSave getInstance() {
        if (gSave == null) gSave = new GSave();
        return gSave;
    }

    public GameMock getGameSave() {
        return gameSave;
    }

    public void saveAllGame(GameController gameController){
        uniqueID = 1;
        gameSave = new GameMock(gameController.getGame(),0);
        System.out.println(gameSave);
        String gameMockJson = new Gson().toJson(gameSave);
        System.out.println(gameMockJson);
        saveAs(gameMockJson,"save" ,0 + ".json");
        System.out.println("game save successfully");
    }

    public Integer save(Technology technology){
        if (technology == null) return null;
        Integer id;
        if ((id = technologyIntegerHashMap.get(technology)) != null) return id;
        id = getUniqueID();
        technologyIntegerHashMap.put(technology,id);
        saveAs(new Gson().toJson(new TechnologyMock(technology,id)),"save",id + ".json");
        return id;
    }

    public ResourceEnum save(Resource resource) {
        if (resource == null) return null;
        return ResourceEnum.getEnumByResource(resource);
    }

    public Integer save(User user) {
        if (user == null) return null;
        Integer id;
        if ((id = userIntegerHashMap.get(user)) != null) return id;
        id = getUniqueID();
        userIntegerHashMap.put(user,id);
        saveAs(new Gson().toJson(user),"save",id + ".json");
        return id;
    }

    public Integer save(Civilization civilization) {
        if (civilization == null) return null;
        Integer id;
        if ((id = civilizationIntegerHashMap.get(civilization)) != null) return id;
        id = getUniqueID();
        civilizationIntegerHashMap.put(civilization,id);
        saveAs(new Gson().toJson(new CivilizationMock(civilization,id)),"save",id + ".json");
        return id;
    }

    public Integer save(GameMap gameMap) {
        if (gameMap == null) return null;
        Integer id;
        if ((id = gameMapIntegerHashMap.get(gameMap)) != null) return id;
        id = getUniqueID();
        gameMapIntegerHashMap.put(gameMap,id);
        saveAs(new Gson().toJson(new GameMapMock(gameMap,id)),"save",id + ".json");
        return id;
    }

    public Integer save(CivilizationMap personalMap) {
        if (personalMap == null) return null;
        Integer id;
        if ((id = civilizationMapIntegerHashMap.get(personalMap)) != null) return id;
        id = getUniqueID();
        civilizationMapIntegerHashMap.put(personalMap,id);
        saveAs(new Gson().toJson(new CivilizationMapMock(personalMap,id)),"save",id + ".json");
        return id;
    }

    public Integer save(City city) {
        if (city == null) return null;
        Integer id;
        if ((id = cityIntegerHashMap.get(city)) != null) return id;
        id = getUniqueID();
        cityIntegerHashMap.put(city,id);
        saveAs(new Gson().toJson(new CityMock(city,id)),"save",id + ".json");
        return id;
    }

    public Integer save(Tile tile) {
        if (tile == null) return null;
        Integer id;
        if ((id = tileIntegerHashMap.get(tile)) != null) return id;
        id = getUniqueID();
        tileIntegerHashMap.put(tile,id);
        saveAs(new Gson().toJson(new TileMock(tile,id)),"save",id + ".json");
        return id;
    }

    public Integer save(Unit unit) {
        if (unit == null) return null;
        Integer id;
        if ((id = unitIntegerHashMap.get(unit)) != null) return id;
        id = getUniqueID();
        unitIntegerHashMap.put(unit,id);
        saveAs(new Gson().toJson(new UnitMock(unit,id)),"save",id + ".json");
        return id;
    }

    public Integer save(Work work) {
        if (work == null) return null;
        Integer id;
        if ((id = workIntegerHashMap.get(work)) != null) return id;
        id = getUniqueID();
        workIntegerHashMap.put(work,id);
        saveAs(new Gson().toJson(new WorkMock(work,id)),"save",id + ".json");
        return id;
    }

    public Integer save(Notification notification) {
        if (notification == null) return null;
        Integer id;
        if ((id = notificationIntegerHashMap.get(notification)) != null) return id;
        id = getUniqueID();
        notificationIntegerHashMap.put(notification,id);
        saveAs(new Gson().toJson(notification),"save",id + ".json");
        return id;
    }

    public Integer save(VisibleTile visibleTile) {
        if (visibleTile == null) return null;
        Integer id;
        if ((id = visibleTileIntegerHashMap.get(visibleTile)) != null) return id;
        id = getUniqueID();
        visibleTileIntegerHashMap.put(visibleTile,id);
        saveAs(new Gson().toJson(new VisibleTileMock(visibleTile,id)),"save",id + ".json");
        return id;
    }

    public ImprovementEnum save(Improvement improvement) {
        if (improvement == null) return null;
        return ImprovementEnum.getImprovementEnumByImprovement(improvement);
    }

    public Integer save(Citizen citizen) {
        if (citizen == null) return null;
        Integer id;
        if ((id = citizenIntegerHashMap.get(citizen)) != null) return id;
        id = getUniqueID();
        citizenIntegerHashMap.put(citizen,id);
        saveAs(new Gson().toJson(new CitizenMock(citizen,id)),"save",id + ".json");
        return id;
    }

    private void saveAs(String string, String loc, String name) {
        try {
            //System.out.println(name);
            FileWriter fileWriter;
            fileWriter = new FileWriter("src/main/resources/app/" + loc + "/" + name);
            fileWriter.write(string);
            fileWriter.close();
        } catch (IOException e) {
            //System.out.println("error for file , " + name);
        }
    }

    private Integer getUniqueID() {
        uniqueID++;
        return uniqueID - 1;
    }


}
