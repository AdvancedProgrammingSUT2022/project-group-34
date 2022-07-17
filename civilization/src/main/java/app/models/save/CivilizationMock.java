package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
import app.models.*;
import app.models.map.CivilizationMap;
import app.models.resource.Resource;
import app.models.resource.ResourceEnum;
import app.models.tile.Tile;
import app.models.unit.Unit;
import app.models.unit.UnitEnum;
import app.models.unit.Work;

import java.util.ArrayList;
import java.util.HashMap;

// note : some class does not have classMock because can be saved
public class CivilizationMock extends Mock{

    private Integer playerID;
    private String civilizationName;
    private Integer personalMapID;

    private final ArrayList<Integer> citiesID = new ArrayList<>();
    private final ArrayList<Integer> workingTilesID = new ArrayList<>();
    private final ArrayList<Integer> unitsID = new ArrayList<>();
    private final ArrayList<Integer> worksID = new ArrayList<>();

    private Integer mainCapitalID;
    private Integer currentCapitalID;

    private final ArrayList<Integer> notificationsID = new ArrayList<>();

    private final HashMap<ResourceEnum, Integer> numberOfEachResourceID = new HashMap<>();
    private final HashMap<ResourceEnum, Integer> numberOfEachExchangedResourceID = new HashMap<>();

    private final ArrayList<String> civilizationNotUsableUnitsID  = new ArrayList<String>();
    private final ArrayList<String> civilizationUsableUnitsID     = new ArrayList<String>();


    private final HashMap<String, Integer> civilizationResearchedTechnologiesID = new HashMap<String, Integer>();
    private final HashMap<String, Integer> civilizationNotResearchedTechnologiesID = new HashMap<String, Integer>();

    private int numberOfBeakers;
    private Integer studyingTechnologyID;

    private int gold;
    private int happiness;
    private int happiness0;
    private int happinessPerLuxuryResource;

    private int unitMaintenanceCost;
    private int roadMaintenanceCost;
    private int railMaintenanceCost;

    private int turn = 0;

    public CivilizationMock(){
        super(0);
    }

    public CivilizationMock(Civilization civilization, Integer id) {
        super(id);
        this.playerID               = GSave.getInstance().save(civilization.getPlayer());
        this.personalMapID = GSave.getInstance().save(civilization.getPersonalMap());
        this.mainCapitalID          = GSave.getInstance().save(civilization.getMainCapital());
        this.currentCapitalID       = GSave.getInstance().save(civilization.getCurrentCapital());
        this.studyingTechnologyID   = GSave.getInstance().save(civilization.getStudyingTechnology());

        civilization.getCities()        .forEach(city -> this.citiesID.add(GSave.getInstance().save(city)));
        civilization.getWorkingTiles()  .forEach(tile -> this.workingTilesID.add(GSave.getInstance().save(tile)));
        civilization.getUnits()         .forEach(unit -> this.unitsID.add(GSave.getInstance().save(unit)));
        civilization.getWorks()         .forEach(work -> this.worksID.add(GSave.getInstance().save(work)));
        civilization.getNotifications() .forEach(notification -> this.notificationsID.add(GSave.getInstance().save(notification)));
        civilization.getCivilizationNotUsableUnits().forEach(unitEnum -> this.civilizationNotUsableUnitsID.add(unitEnum.getName()));
        civilization.getCivilizationUsableUnits()   .forEach(unitEnum -> this.civilizationUsableUnitsID.add(unitEnum.getName()));

        civilization.getNumberOfEachResource().forEach((resource,integer)-> this.numberOfEachResourceID.put(GSave.getInstance().save(resource),integer));
        civilization.getNumberOfEachExchangedResource().forEach((resource,integer)-> this.numberOfEachExchangedResourceID.put(GSave.getInstance().save(resource),integer));
        civilization.getCivilizationResearchedTechnologies()    .forEach((technologyEnum,technology) ->
                this.civilizationResearchedTechnologiesID.put(technologyEnum.getName(),GSave.getInstance().save(technology)));
        civilization.getCivilizationNotResearchedTechnologies() .forEach((technologyEnum, technology) ->
                this.civilizationNotResearchedTechnologiesID.put(technologyEnum.getName(), GSave.getInstance().save(technology)));

        this.numberOfBeakers = civilization.getNumberOfBeakers();
        this.civilizationName = civilization.getCivilizationName();
        this.gold = civilization.getGold();
        this.happiness = civilization.getHappiness();
        this.happiness0 = civilization.getHappiness0();
        this.turn = civilization.getTurn();
        this.happinessPerLuxuryResource = civilization.getHappinessPerLuxuryResource();

        this.unitMaintenanceCost = civilization.getUnitMaintenanceCost();
        this.railMaintenanceCost = civilization.getRailMaintenanceCost();
        this.roadMaintenanceCost = civilization.getRoadMaintenanceCost();

    }

    @Override
    public Civilization getOriginalObject() {
        Civilization civilization = Civilization.getOneInstance();

        civilization.setPlayer((User) GLoad.getInstance().load(new UserMock(),this.playerID));
        civilization.setPersonalMap((CivilizationMap) GLoad.getInstance().load(new CivilizationMapMock(),this.personalMapID));
        civilization.setMainCapital((City) GLoad.getInstance().load(new CityMock(),this.mainCapitalID));
        civilization.setCurrentCapital((City) GLoad.getInstance().load(new CityMock(),this.currentCapitalID));
        civilization.setStudyingTechnology((Technology) GLoad.getInstance().load(new TechnologyMock(),this.studyingTechnologyID));

        ArrayList<City> cities = new ArrayList<>();
        this.citiesID.forEach(id -> cities.add((City) GLoad.getInstance().load(new CityMock(),id)));
        civilization.setCities(cities);

        ArrayList<Tile> workingTiles = new ArrayList<>();
        this.workingTilesID.forEach(id -> workingTiles.add((Tile) GLoad.getInstance().load(new TileMock(),id)));
        civilization.setWorkingTiles(workingTiles);

        ArrayList<Unit> units = new ArrayList<>();
        this.unitsID.forEach(id -> units.add((Unit) GLoad.getInstance().load(new UnitMock(),id)));
        civilization.setUnits(units);

        ArrayList<Work> works = new ArrayList<>();
        this.worksID.forEach(id -> works.add((Work) GLoad.getInstance().load(new WorkMock(),id)));
        civilization.setWorks(works);

        ArrayList<Notification> notifications = new ArrayList<>();
        this.notificationsID.forEach(id -> notifications.add((Notification) GLoad.getInstance().load(new Notification(),id)));
        civilization.setNotifications(notifications);

        ArrayList<UnitEnum> civilizationNotUsableUnits = new ArrayList<>();
        this.civilizationNotUsableUnitsID.forEach(name -> civilizationNotUsableUnits.add(UnitEnum.getEnumByUnitName(name)));
        civilization.setCivilizationNotUsableUnits(civilizationNotUsableUnits);

        ArrayList<UnitEnum> civilizationUsableUnits = new ArrayList<>();
        this.civilizationUsableUnitsID.forEach(name -> civilizationUsableUnits.add(UnitEnum.getEnumByUnitName(name)));
        civilization.setCivilizationUsableUnits(civilizationUsableUnits);

        HashMap<Resource, Integer> numberOfEachResource = new HashMap<>();
        this.numberOfEachResourceID.forEach((resourceEnum, integer) -> numberOfEachResource.put(ResourceEnum.getResourceByEnum(resourceEnum),integer));
        civilization.setNumberOfEachResource(numberOfEachResource);

        HashMap<Resource, Integer> numberOfEachExchangedResource = new HashMap<>();
        this.numberOfEachExchangedResourceID.forEach((resourceEnum, integer) -> numberOfEachExchangedResource.put(ResourceEnum.getResourceByEnum(resourceEnum),integer));
        civilization.setNumberOfEachExchangedResource(numberOfEachExchangedResource);

        HashMap<TechnologyEnum, Technology> civilizationResearchedTechnologies = new HashMap<>();
        this.civilizationResearchedTechnologiesID.forEach((name, integer2) -> civilizationResearchedTechnologies.put(
                TechnologyEnum.getTechnologyEnumByName(name), (Technology) GLoad.getInstance().load(new TechnologyMock(),integer2)));
        civilization.setCivilizationResearchedTechnologies(civilizationResearchedTechnologies);

        HashMap<TechnologyEnum, Technology> civilizationNotResearchedTechnologies = new HashMap<>();
        this.civilizationNotResearchedTechnologiesID.forEach((name, integer2) -> civilizationNotResearchedTechnologies.put(
                TechnologyEnum.getTechnologyEnumByName(name), (Technology) GLoad.getInstance().load(new TechnologyMock(),integer2)));
        civilization.setCivilizationNotResearchedTechnologies(civilizationNotResearchedTechnologies);


        civilization.setNumberOfBeakers(this.numberOfBeakers);
        civilization.setCivilizationName(this.civilizationName);
        civilization.setGold(this.gold);
        civilization.setHappiness(this.happiness);
        civilization.setHappiness0(this.happiness0);
        civilization.setTurn(this.turn);

        civilization.setHappinessPerLuxuryResource(this.happinessPerLuxuryResource);

        civilization.setUnitMaintenanceCost(this.unitMaintenanceCost);
        civilization.setRailMaintenanceCost(this.railMaintenanceCost);
        civilization.setRoadMaintenanceCost(this.roadMaintenanceCost);

        return civilization;
    }

}
