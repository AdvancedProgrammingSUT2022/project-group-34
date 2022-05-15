package models.unit;

import models.Civilization;
import models.tile.Tile;

public class NonCombatUnit extends Unit{
    public NonCombatUnit(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }
}
