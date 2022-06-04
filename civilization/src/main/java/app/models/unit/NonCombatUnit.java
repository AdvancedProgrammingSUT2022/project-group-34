package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

public class NonCombatUnit extends Unit {

    public NonCombatUnit(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }
}
