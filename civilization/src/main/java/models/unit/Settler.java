package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Settler extends NonCombatUnit{

    public boolean isSettle;

    public Settler(String name, Tile position, Civilization civilization) {
        super(name, position, civilization);
    }

    public Settler(String name, Tile position) {
        super(name, position, null);
    }

    public boolean isSettle() {
        return isSettle;
    }

    public void setSettle(boolean settle) {
        isSettle = settle;
    }
}
