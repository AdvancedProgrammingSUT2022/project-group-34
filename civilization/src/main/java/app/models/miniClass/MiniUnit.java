package app.models.miniClass;

import app.controllers.MainServer;
import app.controllers.singletonController.GMini;
import app.models.unit.Unit;

public class MiniUnit {
    private String name;
    private int index;

    public MiniUnit(Unit unit) {//todo
        this.name           = unit.getName();
        this.index = MainServer.getGameControllerByToken(GMini.getGameToken()).getIndex(unit.getCivilization());
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
