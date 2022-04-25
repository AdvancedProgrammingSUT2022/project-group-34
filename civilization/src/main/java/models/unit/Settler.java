package models.unit;

import models.tile.Tile;

public class Settler extends NonCombatUnit{

    public boolean isSettle;

    public Settler(String name, Tile position) {
        super(name, position);
    }

    public boolean isSettle() {
        return isSettle;
    }

    public void setSettle(boolean settle) {
        isSettle = settle;
    }
}
