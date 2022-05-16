package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Settler extends NonCombatUnit{

    public Settler(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

}
