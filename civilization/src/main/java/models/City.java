package models;

import models.tile.Improvement;
import models.tile.Tile;
import models.unit.Unit;

import java.util.ArrayList;

public class City {

    private String name;

    private Civilization owner;
    private Tile position;

    private Unit unitUnderProduct;

    private boolean isGarrison;

    private ArrayList<Tile> territory;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;

    private int food;
    private int gold;
    private int production;

    public City(String name, Civilization owner, Tile position , ArrayList<Tile> territory) {
        this.name = name;
        this.owner = owner;
        this.position = position;
        this.territory = territory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Civilization getOwner() {
        return owner;
    }

    public void setOwner(Civilization owner) {
        this.owner = owner;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public Unit getUnitUnderProduct() {
        return unitUnderProduct;
    }

    public void setUnitUnderProduct(Unit unitUnderProduct) {
        this.unitUnderProduct = unitUnderProduct;
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

    public void addCitizens(Citizen citizen) {
        this.citizens.add(citizen);
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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
}
