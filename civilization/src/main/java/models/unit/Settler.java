package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Settler extends NonCombatUnit{

    public boolean isSettle;

    public Settler(UnitEnum unitEnum, Tile position) {
        super(unitEnum, position);
    }


    public boolean isSettle() {
        return isSettle;
    }

    public void setSettle(boolean settle) {
        isSettle = settle;
    }
}
