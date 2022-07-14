package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

import java.util.ArrayList;

public class Archer extends CombatUnit {

    public int RangedCombatStrength;
    public boolean isSiegeTool;
    public boolean isSetup;
    public ArrayList<String> unitActionList = new ArrayList<>();

    public Archer(UnitEnum unitEnum, Tile position, Civilization civilization) {

        super(unitEnum, position, civilization);
        this.RangedCombatStrength = unitEnum.rangedCombatStrength;
        this.isSiegeTool = unitEnum.isSiegeTool;
        this.isSetup = false;

    }

    public int getRangedCombatStrength() {
        return RangedCombatStrength;
    }

    public void setRangedCombatStrength(int rangedCombatStrength) {
        this.RangedCombatStrength = rangedCombatStrength;
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
