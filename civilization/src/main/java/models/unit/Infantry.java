package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Infantry extends CombatUnit{

    public Infantry(String name, Tile position, Civilization civilization) {
        super(name, position, civilization);
    }

    public void attack(Tile position){
    }
}
