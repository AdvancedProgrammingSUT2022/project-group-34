package app.models;

import app.models.tile.Improvement;
import app.models.tile.Tile;
import app.models.unit.Settler;
import app.models.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class City {

    private static int foodPerCitizens = 2;


    private String name;

    private Civilization civilization;
    private Tile position;
    private int strength;
    private boolean isGarrison;
    private int hitPoint;

    private ArrayList<Tile> territory;
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private ArrayList<Improvement> improvements = new ArrayList<>();

    private HashMap<BuildingEnum,Building> previouslyBuiltBuildings;
    private Building buildingUnderProduct;
    private Unit unitUnderProduct;
    private int productionUnderProductTern;

    private int tillNewCitizen;
    private int foodRate;
    private int food;
    private int productionRate;
    private int production;
    private int scienceRate;

    public City(String name, Civilization owner, Tile position , ArrayList<Tile> territory) {
        this.name = name;
        this.civilization = owner;
        this.position = position;
        this.territory = territory;
        this.strength = territory.size()*5;
        this.hitPoint= 20;
        this.citizens.add(new Citizen(null));
        this.tillNewCitizen = (int) Math.pow(2, citizens.size());
        this.food = 50;
        this.production = 100;
        this.productionRate=20;
        this.foodRate=20;
    }

    public City(String name, Tile position , ArrayList<Tile> territory) {
        this.name = name;
        this.position = position;
        this.territory = territory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Unit getUnitUnderProduct() {
        return unitUnderProduct;
    }

    public void setUnitUnderProduct(Unit unitUnderProduct) {
        this.unitUnderProduct = unitUnderProduct;
    }

    public void setProductionUnderProductTern(int productionUnderProductTern) {
        this.productionUnderProductTern = productionUnderProductTern;
    }

    public int getProductionUnderProductTern() {
        return productionUnderProductTern;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public boolean isGarrison() {
        return isGarrison;
    }

    public void setGarrison(boolean garrison) {
        isGarrison = garrison;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void addCitizens(Citizen citizen) {
        this.citizens.add(citizen);
    }

    public int getFood() {
        return food;
    }

    public void updateFood(boolean Dissatisfaction) {

        foodRate = 0;
        for (Citizen citizen : citizens)
            if (citizen.getWorkPosition() != null)
                foodRate += citizen.getWorkPosition().getFoodRate();
        if (Dissatisfaction)
            foodRate -= foodRate*67/100;
        if (unitUnderProduct instanceof Settler)
            foodRate = 0;
        foodRate -= foodPerCitizens * citizens.size();

        this.food += foodRate;
    }

    // must run after updateFood
    public int updateCitizen(){

        if (this.food < 0){
            int number = citizens.size() - (foodPerCitizens*citizens.size() + food)/foodPerCitizens;
            for (int i = 0; i < number; i++)
                citizens.remove(0);
            this.food += number * foodPerCitizens;
            return -number;
        }

        if (this.food >= getRequiredFoodToProductUnit(citizens.size())) {
            this.food -= getRequiredFoodToProductUnit(citizens.size());
            citizens.add(new Citizen(null));
            return 1;
        }
        return 0;
    }

    public void updateProduction(){
        productionRate = 0;
        for (Citizen citizen : citizens) {
            if (citizen.isWorking())
                productionRate += citizen.getWorkPosition().getProductionRate();
        }
        production += productionRate;

    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public ArrayList<Tile> getTerritory() {
        return territory;
    }

    public void addTerritory(Tile tile) {
        this.territory.add(tile);
    }

    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }
    public void addImprovement(Improvement improvement) {
        this.improvements.add(improvement);
    }
    public int getRequiredFoodToProductUnit(int numberOfCitizens){
        int n = numberOfCitizens;
        return n + n * n / 5;
    }

    public void startProductUnit(Unit unit){
        unitUnderProduct = unit;
        productionUnderProductTern = 12;//TODO Unit.getTern(unit);
    }

    public boolean updateProductUnit(){
        if (productionUnderProductTern == 0)
            return true;
        productionUnderProductTern--;
        return false;
    }


    public int getScienceRate() {
        return scienceRate;
    }

    public void setScienceRate(int numberOfBeakers) {
        this.scienceRate = numberOfBeakers;
    }

    public int getGoldRate() {
        int goldRate=20;
        for (Citizen citizen : citizens)
            if (citizen.isWorking()) goldRate += citizen.getWorkPosition().getGoldRate();

        return goldRate;
    }

    public int getTillNewCitizen() {
        return tillNewCitizen;
    }

    public void setTillNewCitizen(int tillNewCitizen) {
        this.tillNewCitizen = tillNewCitizen;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public HashMap<BuildingEnum, Building> getPreviouslyBuiltBuildings() {
        return previouslyBuiltBuildings;
    }

    public void setPreviouslyBuiltBuildings(HashMap<BuildingEnum, Building> previouslyBuiltBuildings) {
        this.previouslyBuiltBuildings = previouslyBuiltBuildings;
    }

    public Building getBuildingUnderProduct() {
        return buildingUnderProduct;
    }

    public void setBuildingUnderProduct(Building buildingUnderProduct) {
        this.buildingUnderProduct = buildingUnderProduct;
    }

    @Override
    public String toString() {
        return name;
    }
}
