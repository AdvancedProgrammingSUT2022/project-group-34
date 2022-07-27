package app.models.unit;

import app.controllers.GameController;
import app.models.Civilization;
import app.models.tile.AbstractTile;
import app.models.tile.Tile;

public class Unit {

    private String name;
    private int index;

    public Unit(UnitEnum unitEnum, Tile position, Civilization civilization) {

        this.name           = unitEnum.name;
        this.index = GameController.getInstance().getIndex(civilization);
    }

    public Unit(String name, int index) {
        this.index = index;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getCivilizationIndex() {
        return index;
    }

    public Tile getPosition() {
        return null;
    }

    public AbstractTile getDestination() {
        return null;
    }

    public void makeUnitAwake() {

    }

    public void setSleep(boolean b) {

    }

    public void setDestination(AbstractTile position) {

    }
}
