package models;

import models.resource.Resource;
import models.tile.Tile;
import models.unit.Unit;
import models.unit.Work;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {

    private static int decreasedHappinessDueToTheFoundingOfTheCity = 4;


    private User player;

    private String civilizationName;

    private GameMap personalGameMap;

    private ArrayList<City> cities;
    private ArrayList<Tile> territory;
    private ArrayList<Tile> workingTiles;
    private ArrayList<Unit> units;
    private ArrayList<Work> works;

    private City mainCapital;
    private City currentCapital;

    private ArrayList<Notification> notifications;

    private HashMap<String, Resource> civilizationResources;
    private HashMap<String, Integer> numberOfEachResource;
    private HashMap<String, Integer> numberOfEachExchangedResource;

    private HashMap<String, Technology> civilizationResearchedTechnologies;
    private HashMap<String, Technology> civilizationNotResearchedTechnologies;
    private HashMap<String, Unit> producibleUnits;

    private int numberOfBeakers;
    private Technology studyingTechnology;

    private int gold;
    private int production;
    private int happiness;

    public Civilization(User player, String civilizationName, ArrayList<City> cities, ArrayList<Tile> territory, ArrayList<Unit> units, City mainCapital, HashMap<String, Resource> civilizationResources, HashMap<String, Technology> civilizationTechnologies, int numberOfBeakers, int gold, int happy, int production) {
        this.player = player;
        this.civilizationName = civilizationName;
        this.cities = cities;
        this.territory = territory;
        this.units = units;
        this.mainCapital = mainCapital;
        this.civilizationResources = civilizationResources;
        this.civilizationResearchedTechnologies    = Technology.getAllTechnologiesCopy();
        this.civilizationNotResearchedTechnologies = new HashMap<>();

        this.numberOfBeakers = numberOfBeakers;
        this.studyingTechnology = null;

        this.gold = gold;
        this.happiness = happy;
        this.production = production;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public void setCivilizationName(String civilizationName) {
        this.civilizationName = civilizationName;
    }

    public GameMap getPersonalGameMap() {
        return personalGameMap;
    }

    public void setPersonalGameMap(GameMap personalGameMap) {
        this.personalGameMap = personalGameMap;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void addCities(City city) {
        cities.add(city);
        this.happiness -= decreasedHappinessDueToTheFoundingOfTheCity;
        if (cities.size()==1) setMainCapital(city);
        this.getTerritory().addAll(city.getTerritory());
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<Tile> getTerritory() {
        return territory;
    }

    public void setTerritory(ArrayList<Tile> territory) {
        this.territory = territory;
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

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public HashMap<String, Unit> getProducibleUnits() {
        return producibleUnits;
    }

    public void setProducibleUnits(HashMap<String, Unit> producibleUnits) {
        this.producibleUnits = producibleUnits;
    }



    public City getCityByName(String name){
        for (City city : cities) {
            if (city.getName().equals(name)) return city;
        }
        return null;
    }

    public ArrayList<Work> getWorks() {
        return works;
    }

    public void addWork(Work work) {
        this.works.add(work);
    }

    public void setWorks(ArrayList<Work> works) {
        this.works = works;
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
}
