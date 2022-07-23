package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

public class Worker extends NonCombatUnit{

    public Worker(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }
}
