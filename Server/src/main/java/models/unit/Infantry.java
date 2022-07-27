package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Infantry extends CombatUnit{

    public Infantry(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

    public void attack(Tile position){
    }
}
