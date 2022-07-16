package app;

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
import app.models.unit.UnitEnum;
import app.models.unit.Work;
import com.google.gson.Gson;

import java.util.HashMap;

public class GSave {

    private Integer uniqueID;
    private HashMap<String,Integer> uniqueIDForObject;

    private HashMap<Technology,Integer> technologyIntegerHashMap = new HashMap<>();
    private HashMap<Civilization,Integer> civilizationIntegerHashMap = new HashMap<>();
    private HashMap<User,Integer> userIntegerHashMap = new HashMap<>();
    private HashMap<GameMap,Integer> gameMapIntegerHashMap = new HashMap<>();
    private HashMap<CivilizationMap,Integer> civilizationMapIntegerHashMap = new HashMap<>();
    private HashMap<City,Integer> cityIntegerHashMap = new HashMap<>();
    private HashMap<Work,Integer> workIntegerHashMap = new HashMap<>();
    private HashMap<Notification,Integer> notificationIntegerHashMap = new HashMap<>();
    private HashMap<UnitEnum, Integer> unitEnumIntegerHashMap = new HashMap<>();

    private HashMap<TechnologyEnum, Integer> technologyEnumIntegerHashMap = new HashMap<>();
    private HashMap<Citizen, Integer> citizenIntegerHashMap = new HashMap<>();
    private HashMap<VisibleTile, Integer> visibleTileIntegerHashMap = new HashMap<>();
    private HashMap<Tile, Integer> tileIntegerHashMap = new HashMap<>();
    private HashMap<Unit, Integer> unitIntegerHashMap = new HashMap<>();


    private static GSave gSave;

    private GSave(){
        uniqueID = 0;
    }

    public static GSave getInstance() {
        if (gSave == null) gSave = new GSave();
        return gSave;
    }

    public Integer save(Technology technology){
        if (technology == null) return null;
        Integer id;
        if ((id = technologyIntegerHashMap.get(technology)) != null) return id;
        id = getUniqueID();
        technologyIntegerHashMap.put(technology,id);
        saveAs(new Gson().toJson(new MockTechnology(technology,id)),id + ".json");
        return id;
    }

    public ResourceEnum save(Resource resource) {
        if (resource == null) return null;
        return ResourceEnum.getResourceEnumByResource(resource);
    }

    public Integer save(User user) {
        if (user == null) return null;
        Integer id;
        if ((id = userIntegerHashMap.get(user)) != null) return id;
        id = getUniqueID();
        userIntegerHashMap.put(user,id);
        saveAs(new Gson().toJson(user),id + ".json");
        return id;
    }

    public Integer save(Civilization civilization) {
        if (civilization == null) return null;
        Integer id;
        if ((id = civilizationIntegerHashMap.get(civilization)) != null) return id;
        id = getUniqueID();
        civilizationIntegerHashMap.put(civilization,id);
        saveAs(new Gson().toJson(new MockCivilization(civilization,id)),id + ".json");
        return id;
    }

    public Integer save(GameMap gameMap) {
        if (gameMap == null) return null;
        Integer id;
        if ((id = gameMapIntegerHashMap.get(gameMap)) != null) return id;
        id = getUniqueID();
        gameMapIntegerHashMap.put(gameMap,id);
        saveAs(new Gson().toJson(new MockGameMap(gameMap,id)),id + ".json");
        return id;
    }

    public Integer save(CivilizationMap personalMap) {
        if (personalMap == null) return null;
        Integer id;
        if ((id = civilizationMapIntegerHashMap.get(personalMap)) != null) return id;
        id = getUniqueID();
        civilizationMapIntegerHashMap.put(personalMap,id);
        saveAs(new Gson().toJson(new MockCivilizationMap(personalMap,id)),id + ".json");
        return id;
    }

    public Integer save(City city) {
        if (city == null) return null;
        Integer id;
        if ((id = cityIntegerHashMap.get(city)) != null) return id;
        id = getUniqueID();
        cityIntegerHashMap.put(city,id);
        saveAs(new Gson().toJson(new CityMock(city,id)),id + ".json");
        return id;
    }

    public Integer save(Tile tile) {
        if (tile == null) return null;
        Integer id;
        if ((id = tileIntegerHashMap.get(tile)) != null) return id;
        id = getUniqueID();
        tileIntegerHashMap.put(tile,id);
        saveAs(new Gson().toJson(new TileMock(tile,id)),id + ".json");
        return id;
    }

    public Integer save(Unit unit) {
        if (unit == null) return null;
        Integer id;
        if ((id = unitIntegerHashMap.get(unit)) != null) return id;
        id = getUniqueID();
        unitIntegerHashMap.put(unit,id);
        saveAs(new Gson().toJson(new UnitMock(unit,id)),id + ".json");
        return id;
    }

    public Integer save(Work work) {
        if (work == null) return null;
        Integer id;
        if ((id = workIntegerHashMap.get(work)) != null) return id;
        id = getUniqueID();
        workIntegerHashMap.put(work,id);
        saveAs(new Gson().toJson(new WorkMock(work,id)),id + ".json");
        return id;
    }

    public Integer save(Notification notification) {
        if (notification == null) return null;
        Integer id;
        if ((id = notificationIntegerHashMap.get(notification)) != null) return id;
        id = getUniqueID();
        notificationIntegerHashMap.put(notification,id);
        saveAs(new Gson().toJson(notification),id + ".json");
        return id;
    }

    public Integer save(UnitEnum unitEnum) {
        if (unitEnum == null) return null;
        Integer id;
        if ((id = unitEnumIntegerHashMap.get(unitEnum)) != null) return id;
        id = getUniqueID();
        unitEnumIntegerHashMap.put(unitEnum,id);
        saveAs(new Gson().toJson(unitEnum),id + ".json");
        return id;
    }

    public Integer save(TechnologyEnum technologyEnum) {
        if (technologyEnum == null) return null;
        Integer id;
        if ((id = technologyEnumIntegerHashMap.get(technologyEnum)) != null) return id;
        id = getUniqueID();
        technologyEnumIntegerHashMap.put(technologyEnum,id);
        saveAs(new Gson().toJson(technologyEnum),id + ".json");
        return id;
    }

    public Integer save(VisibleTile visibleTile) {
        if (visibleTile == null) return null;
        Integer id;
        if ((id = visibleTileIntegerHashMap.get(visibleTile)) != null) return id;
        id = getUniqueID();
        visibleTileIntegerHashMap.put(visibleTile,id);
        saveAs(new Gson().toJson(new VisibleTileMock(visibleTile,id)),id + ".json");
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
        saveAs(new Gson().toJson(new CitizenMock(citizen,id)),id + ".json");
        return id;
    }

    private void saveAs(String string, String name) {
        //todo
    }

    private Integer getUniqueID() {
        uniqueID++;
        return uniqueID - 1;
    }
}
