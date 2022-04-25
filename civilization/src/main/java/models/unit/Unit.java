package models.unit;

import models.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {

    public static HashMap<String, ArrayList<String>> dataBaseRequiredTechnology;
    public static HashMap<String, HashMap<String, String>> unitDataSheet = new HashMap<>();
    public static int motionPointConstant;

    protected String name;
    protected int promotion;
    protected int cost;

    protected int motionPoint;

    protected Tile position;
    protected Tile destination;
    protected boolean isMoving;

    public Unit(String name, Tile position) {
        this.name = name;
        this.position = position;

        this.promotion           = Integer.parseInt(unitDataSheet.get(name).get("promotion"));
        this.cost                = Integer.parseInt(unitDataSheet.get(name).get("cost"));
        this.motionPoint         = Integer.parseInt(unitDataSheet.get(name).get("motionPoint"));

        this.destination = null;
        this.isMoving    = false;
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
        destination = destination;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isUnlock(String name,ArrayList<String> technologies) {

        if (dataBaseRequiredTechnology.get(name) == null)
            return false;

        for (String technology1: dataBaseRequiredTechnology.get(name)) {

            boolean flag = false;
            for (String technology2 : technologies) {
                if (technology1.equals(technology2))
                    flag = true;
            }

            if (!flag)
                return false;
        }

        return true;
    }
}
