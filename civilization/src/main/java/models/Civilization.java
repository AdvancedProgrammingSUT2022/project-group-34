package models;

import models.resource.LuxuryResource;
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
    private HashMap<Resource, Integer> numberOfEachResource;
    private HashMap<String, Integer> numberOfEachExchangedResource;

    private HashMap<String, Technology> civilizationResearchedTechnologies;
    private HashMap<String, Technology> civilizationNotResearchedTechnologies;

    private int numberOfBeakers;
    private Technology studyingTechnology;

    private int gold;
<<<<<<< HEAD
<<<<<<< HEAD
    private int production;
    private int happiness;

    public Civilization(User player, String civilizationName, ArrayList<City> cities, ArrayList<Tile> territory, ArrayList<Unit> units, City mainCapital, HashMap<String, Resource> civilizationResources, HashMap<String, Technology> civilizationTechnologies, int numberOfBeakers, int gold, int happy, int production) {
=======
=======
>>>>>>> 04826e8f7c90fc902ad1c77836c97178bf38eae1
    private int goldRate;
    private int happiness;
    private int happiness0;
    private int happinessPerLuxuryResource = 4;
    private int unitMaintenanceCost = 2;
    private int roadMaintenanceCost = 1;
    private int railMaintenanceCost = 1;

    public Civilization(User player, String civilizationName, ArrayList<City> cities, ArrayList<Tile> territory, ArrayList<Unit> units, City mainCapital, int numberOfBeakers, int gold, int happiness, int happiness0) {
<<<<<<< HEAD
>>>>>>> e471f5659063c59aedd91973e4ac849ef439bd7c
=======
>>>>>>> 04826e8f7c90fc902ad1c77836c97178bf38eae1
        this.player = player;
        this.civilizationName = civilizationName;
        this.cities = cities;
        this.territory = territory;
        this.units = units;
        this.mainCapital = mainCapital;
        this.civilizationResources = Resource.getAllResourcesCopy();
        this.civilizationResearchedTechnologies    = Technology.getAllTechnologiesCopy();
        this.civilizationNotResearchedTechnologies = new HashMap<>();

        this.numberOfBeakers = numberOfBeakers;
        this.studyingTechnology = null;

        this.gold = gold;
<<<<<<< HEAD
<<<<<<< HEAD
        this.happiness = happy;
        this.production = production;
=======
        this.happiness0 = happiness0;
        this.happiness = happiness;
>>>>>>> e471f5659063c59aedd91973e4ac849ef439bd7c
=======
        this.happiness0 = happiness0;
        this.happiness = happiness;
>>>>>>> 04826e8f7c90fc902ad1c77836c97178bf38eae1
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

    public void updateGold() {

        this.goldRate = 0;
        for (City city : cities)
            for (Citizen citizen : city.getCitizens())
                if (citizen.isWorking())
                    goldRate += citizen.getWorkPosition().getGoldRate();

        for (Unit unit : units) {
            goldRate -= unitMaintenanceCost;
        }

<<<<<<< HEAD
<<<<<<< HEAD
    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
=======
=======
>>>>>>> 04826e8f7c90fc902ad1c77836c97178bf38eae1
        for (Tile tile : territory) {
            if (tile.isHasRoad())
                goldRate -= roadMaintenanceCost;
            if (tile.isHasRail())
                goldRate -= railMaintenanceCost;
        }


        this.gold += this.goldRate;
    }

    public int getHappiness() {
        return happiness;
<<<<<<< HEAD
>>>>>>> e471f5659063c59aedd91973e4ac849ef439bd7c
=======
>>>>>>> 04826e8f7c90fc902ad1c77836c97178bf38eae1
    }

    public boolean isUnHappy(){
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

    public ArrayList<Work> getWorks() {
        return works;
    }

    public void addWork(Work work) {
        this.works.add(work);
    }

    public void removeUnit(Unit unit){
        units.remove(unit);
    }
}
