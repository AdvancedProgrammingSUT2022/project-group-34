package app.models.unit;

import app.models.Civilization;
import app.models.tile.Tile;

public class Worker extends NonCombatUnit{

    public boolean isWorking = false;
    public int ternWork = 0;


    public Worker(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void startWork() {
        setWorking(true);
    }

    public void finishWork() {
        setWorking(false);
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public int getTernWork() {
        return ternWork;
    }

    public void setTernWork(int ternWork) {
        this.ternWork = ternWork;
    }
}
