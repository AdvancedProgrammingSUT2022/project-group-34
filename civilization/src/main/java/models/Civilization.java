package models;

import models.resource.Resource;
import models.tile.Tile;
import models.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {

    private User player;

    private String civilizationName;

    private GameMap personalGameMap;

    private ArrayList<City> cities;
    private ArrayList<Tile> territory;
    private ArrayList<Tile> workingTiles;
    private ArrayList<Unit> units;

    private City mainCapital;
    private City currentCapital;

    private ArrayList<Notification> notifications;
    private HashMap<String, Resource> civilizationResources;
    private HashMap<String, Technology> civilizationTechnologies;

    private int numberOfBeakers;
    private Technology studyingTechnology;

    private int gold;
    private int food;
    private int production;
    private int happy;

    public Civilization(User player, String civilizationName, ArrayList<City> cities, ArrayList<Tile> territory, ArrayList<Unit> units, City mainCapital, HashMap<String, Resource> civilizationResources, HashMap<String, Technology> civilizationTechnologies, int numberOfBeakers, int gold, int happy, int production) {
        this.player = player;
        this.civilizationName = civilizationName;
        this.cities = cities;
        this.territory = territory;
        this.units = units;
        this.mainCapital = mainCapital;
        this.civilizationResources = civilizationResources;
        this.civilizationTechnologies = civilizationTechnologies;

        this.numberOfBeakers = numberOfBeakers;
        this.studyingTechnology = null;

        this.gold = gold;
        this.happy = happy;
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

    public HashMap<String, Technology> getCivilizationTechnologies() {
        return civilizationTechnologies;
    }

    public void setCivilizationTechnologies(HashMap<String, Technology> civilizationTechnologies) {
        this.civilizationTechnologies = civilizationTechnologies;
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

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }
}