package app.models.unit;

import app.models.resource.ResourceData;
import app.models.City;
import app.models.Civilization;
import app.models.TechnologyEnum;
import app.models.tile.Tile;

public class CombatUnit extends Unit {

    public CombatUnit(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

    public void setAlert(boolean b) {

    }

    public void setFortifyUntilHealed(boolean b) {

    }

    public void setFortify(boolean b) {

    }
}
