package models.unit;

import models.tile.Tile;

import java.util.HashMap;

public class Archer extends CombatUnit{

    public static HashMap<String, HashMap<String, String>> archerDataSheet = new HashMap<>();

    public int remoteCombatPower;
    public boolean isSiegeTool;
    public boolean isSetup;

    public Archer(String name, Tile position) {
        super(name, position);
    }

    public static HashMap<String, HashMap<String, String>> getArcherDataSheet() {
        return archerDataSheet;
    }

    public static void loadDataSheet() {
        Archer.archerDataSheet = null; // todo Read from file
    }

    public int getRemoteCombatPower() {
        return remoteCombatPower;
    }

    public void setRemoteCombatPower(int remoteCombatPower) {
        this.remoteCombatPower = remoteCombatPower;
    }

    public boolean isSiegeTool() {
        return isSiegeTool;
    }

    public void setSiegeTool(boolean siegeTool) {
        isSiegeTool = siegeTool;
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
    }

    @Override
    public void makeUnitAwake() {
        super.makeUnitAwake();
        setSetup(false);
    }

    public void attack(Tile position){
    }
}
