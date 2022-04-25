package views;

import views.Tile.Tile;

public class Citizen {

    public boolean isWorking;
    public Tile workPosition;

    public Citizen(Tile workPosition) {
        this.workPosition = workPosition;
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
