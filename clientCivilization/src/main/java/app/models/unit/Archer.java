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

    public void setSetup(boolean b) {

    }
}
