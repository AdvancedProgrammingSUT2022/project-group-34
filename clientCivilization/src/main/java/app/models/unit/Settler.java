package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

public class Settler extends NonCombatUnit{

    public Settler(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

}
