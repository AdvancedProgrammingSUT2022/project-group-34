package models.unit;

import models.Civilization;
import models.tile.Tile;

public class Worker extends NonCombatUnit{

    public boolean isWorking;
    public int ternWork;

    public Worker(String name, Tile position, Civilization civilization) {
        super(name, position, civilization);

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
