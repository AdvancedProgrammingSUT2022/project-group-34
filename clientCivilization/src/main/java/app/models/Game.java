package app.models;

import app.models.map.GameMap;

import java.util.ArrayList;

public class Game {

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private GameMap mainGameMap;


    public Game() {
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public GameMap getMainGameMap() {
        return mainGameMap;
    }

    public void setMainGameMap(GameMap mainGameMap) {
        this.mainGameMap = mainGameMap;
    }


}
