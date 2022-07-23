package app.models.miniClass;

import app.controllers.GameController;
import app.models.unit.Unit;

public class MiniUnit {
    private String name;
    private int index;

    public MiniUnit(Unit unit) {
        this.name           = unit.getName();
        this.index = GameController.getInstance().getIndex(unit.getCivilization());
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
