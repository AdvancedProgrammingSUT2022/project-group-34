package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

public class Infantry extends CombatUnit{

    public Infantry(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

}
