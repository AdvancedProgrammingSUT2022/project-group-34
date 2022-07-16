package app.models.save;

import app.GSave;
import app.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MockCivilization extends Mock{

    private Integer id;

    private Integer playerID;

    private String civilizationName;

    private Integer personalMap;

    private ArrayList<Integer> citiesID = new ArrayList<>();
    private ArrayList<Integer> workingTilesID = new ArrayList<>();
    private ArrayList<Integer> unitsID = new ArrayList<>();
    private ArrayList<Integer> worksID = new ArrayList<>();

    private Integer mainCapitalID;
    private Integer currentCapitalID;

    private ArrayList<Integer> notificationsID = new ArrayList<>();

    private final HashMap<Integer, Integer> numberOfEachResourceID = new HashMap<>();
    private final HashMap<Integer, Integer> numberOfEachExchangedResourceID = new HashMap<>();

    private ArrayList<Integer> civilizationNotUsableUnitsID  = new ArrayList<>();
    private ArrayList<Integer> civilizationUsableUnitsID     = new ArrayList<>();


    private HashMap<Integer, Integer> civilizationResearchedTechnologiesID = new HashMap<>();
    private HashMap<Integer, Integer> civilizationNotResearchedTechnologiesID = new HashMap<>();

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

    public MockCivilization(Civilization civilization, Integer id) {
        super(id);
        this.playerID               = GSave.getInstance().save(civilization.getPlayer());
        this.personalMap            = GSave.getInstance().save(civilization.getPersonalMap());
        this.mainCapitalID          = GSave.getInstance().save(civilization.getMainCapital());
        this.currentCapitalID       = GSave.getInstance().save(civilization.getCurrentCapital());
        this.studyingTechnologyID   = GSave.getInstance().save(civilization.getStudyingTechnology());

        civilization.getCities()        .forEach(city -> this.citiesID.add(GSave.getInstance().save(city)));
        civilization.getWorkingTiles()  .forEach(tile -> this.workingTilesID.add(GSave.getInstance().save(tile)));
        civilization.getUnits()         .forEach(unit -> this.unitsID.add(GSave.getInstance().save(unit)));
        civilization.getWorks()         .forEach(work -> this.worksID.add(GSave.getInstance().save(work)));
        civilization.getNotifications() .forEach(notification -> this.notificationsID.add(GSave.getInstance().save(notification)));
        civilization.getCivilizationNotUsableUnits().forEach(unitEnum -> this.civilizationNotUsableUnitsID.add(GSave.getInstance().save(unitEnum)));
        civilization.getCivilizationUsableUnits()   .forEach(unitEnum -> this.civilizationUsableUnitsID.add(GSave.getInstance().save(unitEnum)));
        civilization.getCivilizationResearchedTechnologies()    .forEach((technologyEnum,technology) ->
                this.civilizationResearchedTechnologiesID.put(GSave.getInstance().save(technologyEnum),GSave.getInstance().save(technology)));
        civilization.getCivilizationNotResearchedTechnologies() .forEach((technologyEnum, technology) ->
                this.civilizationNotResearchedTechnologiesID.put(GSave.getInstance().save(technologyEnum), GSave.getInstance().save(technology)));

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
        return null;
    }
}
