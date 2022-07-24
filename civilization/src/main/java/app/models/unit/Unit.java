package app.models.unit;

import app.models.Civilization;
import app.models.Technology;
import app.models.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Unit {

    public static HashMap<UnitEnum, Unit> unitEnumUnitHashMap = new HashMap<>();
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

    public Unit(UnitEnum unitEnum, Tile position, Civilization civilization) {

        this.name           = unitEnum.name;
        this.movement       = unitEnum.movement;
        this.cost           = unitEnum.cost;
        this.destination    = null;
        this.isMoving       = false;

        this.position = position;
        this.unitActionList = new ArrayList<>();
        //TODO
        this.unitActionList.add("");
        this.unitActionList.add("a");
        this.unitActionList.add("b");
        this.unitActionList.add("c");

        this.civilization = civilization;
    }


    public static void loadUnitEnumUNitHashMap() {
        for (UnitEnum unitEnum : UnitEnum.values()) {
            switch (unitEnum.type){
                case "S":
                    unitEnumUnitHashMap.put(unitEnum,new Settler(unitEnum,null,null));
                    break;
                case "W":
                    unitEnumUnitHashMap.put(unitEnum,new Worker(unitEnum,null,null));
                    break;
                case "A":
                    unitEnumUnitHashMap.put(unitEnum,new Archer(unitEnum,null,null));
                    break;
                case "I":
                    unitEnumUnitHashMap.put(unitEnum,new Infantry(unitEnum,null,null));
                    break;
            }
        }
    }

    public static Unit getUnitByUnitEnum(UnitEnum unitEnum) {
        if (unitEnumUnitHashMap.size() == 0){
            loadUnitEnumUNitHashMap();
        }
        return unitEnumUnitHashMap.get(unitEnum);
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

    public static boolean isUnlock(String name,ArrayList<String> technologies) {

        Technology technology1 = Technology.getTechnologyByTechnologyEnum(UnitEnum.valueOf(name).getRequiredTechnology());
        if (technology1 == null) return false;

        for (String technology2 : technologies) {
            if (technology1.getName().equals(technology2)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<String> getUnitActionList() {
        return unitActionList;
    }

    public void makeUnitAwake(){
        setSleep(false);
    }

    @Override
    public String toString() {
        return name;
    }
}
