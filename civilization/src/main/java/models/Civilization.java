package models;

import models.map.CivilizationMap;
import models.resource.LuxuryResource;
import models.resource.Resource;
import models.tile.AbstractTile;
import models.tile.Tile;
import models.unit.Unit;
import models.unit.Work;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {

    private static int decreasedHappinessDueToTheFoundingOfTheCity = 4;


    private User player;

    private String civilizationName;

    private CivilizationMap personalMap;

    private ArrayList<City> cities;
    private ArrayList<Tile> workingTiles;
    private ArrayList<Unit> units;
    private ArrayList<Work> works;

    private City mainCapital;
    private City currentCapital;

    private ArrayList<Notification> notifications;

    private HashMap<String, Resource> civilizationResources;
    private HashMap<Resource, Integer> numberOfEachResource = new HashMap<>();
    private HashMap<String, Integer> numberOfEachExchangedResource = new HashMap<>();

    private HashMap<String, Technology> civilizationResearchedTechnologies;
    private HashMap<String, Technology> civilizationNotResearchedTechnologies;
    private HashMap<String, Unit> producibleUnits;

    private int numberOfBeakers;
    private Technology studyingTechnology;

    private int gold;
    private int goldRate;
    private int happiness;
    private int happiness0;
    private int happinessPerLuxuryResource = 4;
    private int unitMaintenanceCost = 2;
    private int roadMaintenanceCost = 1;
    private int railMaintenanceCost = 1;

    private int turn = 0;

    public Civilization(User player, String civilizationName, ArrayList<City> cities, ArrayList<Unit> units, City mainCapital, int numberOfBeakers, int gold, int happiness, int happiness0) {
        this.player = player;
        this.civilizationName = civilizationName;
        this.cities = cities;
        this.units = units;
        this.mainCapital = mainCapital;
        this.works = new ArrayList<>();
        this.civilizationResources = Resource.getAllResourcesCopy();
        this.civilizationResearchedTechnologies = Technology.getAllTechnologiesCopy();
        this.civilizationNotResearchedTechnologies = new HashMap<>();

        this.numberOfBeakers = numberOfBeakers;
        this.studyingTechnology = null;

        this.gold = gold;
        this.happiness0 = happiness0;
        this.happiness = happiness;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public void setCivilizationName(String civilizationName) {
        this.civilizationName = civilizationName;
    }

    public CivilizationMap getPersonalMap() {
        return personalMap;
    }

    public void setPersonalMap(CivilizationMap personalMap) {
        this.personalMap = personalMap;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void addCities(City city) {
        cities.add(city);
        this.happiness -= decreasedHappinessDueToTheFoundingOfTheCity;
        if (cities.size() == 1) setMainCapital(city);
        this.getTerritory().addAll(city.getTerritory());
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<AbstractTile> getTerritory() {
        ArrayList<AbstractTile> territory = new ArrayList<>();
        for (City city : cities) territory.addAll(city.getTerritory());
        return territory;
    }

    public ArrayList<Tile> getWorkingTiles() {
        return workingTiles;
    }

    public void setWorkingTiles(ArrayList<Tile> workingTiles) {
        this.workingTiles = workingTiles;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public City getMainCapital() {
        return mainCapital;
    }

    public void setMainCapital(City mainCapital) {
        this.mainCapital = mainCapital;
    }

    public City getCurrentCapital() {
        return currentCapital;
    }

    public void setCurrentCapital(City currentCapital) {
        this.currentCapital = currentCapital;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public HashMap<String, Resource> getCivilizationResources() {
        return civilizationResources;
    }

    public void setCivilizationResources(HashMap<String, Resource> civilizationResources) {
        this.civilizationResources = civilizationResources;
    }

    public HashMap<String, Technology> getCivilizationResearchedTechnologies() {
        return civilizationResearchedTechnologies;
    }

    public void setCivilizationResearchedTechnologies(HashMap<String, Technology> civilizationResearchedTechnologies) {
        this.civilizationResearchedTechnologies = civilizationResearchedTechnologies;
    }

    public HashMap<String, Technology> getCivilizationNotResearchedTechnologies() {
        return civilizationNotResearchedTechnologies;
    }

    public void setCivilizationNotResearchedTechnologies(HashMap<String, Technology> civilizationNotResearchedTechnologies) {
        this.civilizationNotResearchedTechnologies = civilizationNotResearchedTechnologies;
    }

    public int getNumberOfBeakers() {
        return numberOfBeakers;
    }

    public void setNumberOfBeakers(int numberOfBeakers) {
        this.numberOfBeakers = numberOfBeakers;
    }

    public Technology getStudyingTechnology() {
        return studyingTechnology;
    }

    public void setStudyingTechnology(Technology studyingTechnology) {
        this.studyingTechnology = studyingTechnology;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void updateGold() {

        this.goldRate = 0;
        for (City city : cities)
            for (Citizen citizen : city.getCitizens())
                if (citizen.isWorking())
                    goldRate += citizen.getWorkPosition().getGoldRate();

        for (Unit unit : units) {
            goldRate -= unitMaintenanceCost;
        }

        for (AbstractTile tile : getTerritory()) {
            if (((Tile)tile).HasRoad())
                goldRate -= roadMaintenanceCost;
            if (((Tile)tile).HasRail())
                goldRate -= railMaintenanceCost;
        }


        this.gold += this.goldRate;
    }

    public int getHappiness() {
        return happiness;
    }

    public boolean isUnHappy() {
        return happiness < 0;
    }

    public void setHappiness() {
        int n = cities.size();
        int m = 0;
        for (City city : cities)
            m += city.getCitizens().size();

        this.happiness = happiness0 - (n + n * n / 8) - (m + m * m / 8);

        numberOfEachResource.forEach((key, value) -> {

            if (key instanceof LuxuryResource)
                if (value - numberOfEachExchangedResource.get(key) != 0) {
                    happiness += happinessPerLuxuryResource;
                }
        });
    }

    public HashMap<String, Unit> getProducibleUnits() {
        return producibleUnits;
    }

    public void setProducibleUnits(HashMap<String, Unit> producibleUnits) {
        this.producibleUnits = producibleUnits;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) return city;
        }
        return null;
    }

    public ArrayList<Work> getWorks() {
        return works;
    }

    public boolean isInFog(Tile tile) {
        return personalMap.getTileByXY(tile.getX(), tile.getY()).isInFog();
    }

    public boolean isTransparent(Tile tile) {
        return personalMap.isTransparent(personalMap.getTileByXY(tile.getX(), tile.getY()));
    }

    public void addWork(Work work) {
        this.works.add(work);
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public int getPopulation() {
        int population = 0;
        for (City city : cities)
            population += city.getCitizens().size();

        return population;
    }

    public Work getWorkByTile(Tile tile) {
        for (Work work : works)
            if (work.getTile().equals(tile)) return work;

        return null;
    }
}
