package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Settler extends NonCombatUnit{

    public boolean isSettle;

    public Settler(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

    public Settler(UnitEnum unitEnum, Tile position) {
        super(unitEnum, position, null);
    }

    public boolean isSettle() {
        return isSettle;
    }

    public void setSettle(boolean settle) {
        isSettle = settle;
    }
}
