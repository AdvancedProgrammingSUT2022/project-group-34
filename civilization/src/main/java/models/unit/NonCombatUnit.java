package models.unit;

import models.Civilization;
import models.tile.Tile;

public class NonCombatUnit extends Unit{
    public NonCombatUnit(String name, Tile position, Civilization civilization) {
        super(name, position, civilization);
    }
}
