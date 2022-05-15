package models.unit;

import models.Civilization;
import models.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Unit {

    public static HashMap<String, ArrayList<String>> dataBaseRequiredTechnology;
    public static HashMap<String, HashMap<String, String>> unitDataSheet = new HashMap<>();
    public static int motionPointConstant;
    public static int healthConstant;

    private String name;
    private int movement;
    private int cost;
    private int motionPoint;
    protected boolean isSleep;

    private Stack<Tile> path;
    private boolean isMoving;
    private ArrayList<String> unitActionList;

    private Tile position;
    private Tile destination;

    private Civilization civilization;

    public Unit(UnitEnum unitEnum, Tile position) {

        this.name           = unitEnum.name;
        this.movement       = unitEnum.movement;
        this.cost           = unitEnum.cost;
        this.destination    = null;
        this.isMoving       = false;

        this.position = position;
        this.unitActionList = new ArrayList<>(unitActionList);
        //TODO
        this.unitActionList.add("");
        this.unitActionList.add("a");
        this.unitActionList.add("b");
        this.unitActionList.add("c");
    }

    public static HashMap<String, ArrayList<String>> getDataBaseRequiredTechnology() {
        return dataBaseRequiredTechnology;
    }

    public static HashMap<String, HashMap<String, String>> getUnitDataSheet() {
        return unitDataSheet;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
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

    public boolean isSleep() {
        return isSleep;
    }

    public void setSleep(boolean sleep) {
        isSleep = sleep;
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

    public ArrayList<String> getUnitActionList() {
        return unitActionList;
    }

    public void makeUnitAwake(){
        setSleep(false);
    }
}
