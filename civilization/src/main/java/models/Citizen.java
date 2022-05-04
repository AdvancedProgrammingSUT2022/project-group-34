package models;

import models.tile.Tile;

public class Citizen {

    private boolean isWorking;
    private Tile workPosition;

    public Citizen(Tile workPosition) {
        this.workPosition = workPosition;
        isWorking = false;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public Tile getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(Tile workPosition) {
        this.workPosition = workPosition;
    }
}
