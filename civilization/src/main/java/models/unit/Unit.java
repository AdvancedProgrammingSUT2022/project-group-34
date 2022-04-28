package models.unit;

import models.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Unit {

    public static HashMap<String, ArrayList<String>> dataBaseRequiredTechnology;
    public static HashMap<String, HashMap<String, String>> unitDataSheet = new HashMap<>();
    public static int motionPointConstant;
    public static int healthConstant;

    protected String name;
    protected int movementSpeed;
    protected int promotion;
    protected int cost;

    protected int motionPoint;

    protected Tile position;
    protected Tile destination;

    public Unit(String name, Tile position) {
        this.name = name;
        this.position = position;

        this.movementSpeed       = Integer.parseInt(unitDataSheet.get(name).get("movementSpeed"));
        this.promotion           = Integer.parseInt(unitDataSheet.get(name).get("promotion"));
        this.cost                = Integer.parseInt(unitDataSheet.get(name).get("cost"));
        this.motionPoint         = Integer.parseInt(unitDataSheet.get(name).get("motionPoint"));

        this.destination = null;
    }

    public static HashMap<String, ArrayList<String>> getDataBaseRequiredTechnology() {
        return dataBaseRequiredTechnology;
    }

    public static HashMap<String, HashMap<String, String>> getUnitDataSheet() {
        return unitDataSheet;
    }

    public static void loadDataSheet() {
        Unit.dataBaseRequiredTechnology = null; // todo Read from file
        Unit.unitDataSheet              = null; // todo Read from file
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMotionPoint() {
        return motionPoint;
    }

    public void setMotionPoint(int motionPoint) {
        this.motionPoint = motionPoint;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public Tile getDestination() {
        return destination;
    }

    public void setDestination(Tile destination) {
        this.destination = destination;
    }

    public boolean isUnlock(String name,ArrayList<String> technologies) {

        if (dataBaseRequiredTechnology.get(name) == null)
            return false;

        for (String technology1: dataBaseRequiredTechnology.get(name)) {

            boolean flag = false;
            for (String technology2 : technologies) {
                if (technology1.equals(technology2)) {
                    flag = true;
                    break;
                }
            }

            if (!flag)
                return false;
        }

        return true;
    }
}
