package controllers;

import models.Civilization;

public class GameController {
    //Singleton Pattern
    private static GameController instance;

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }


    //Fields of the class
    private int civilizationIndex;
    private Civilization civilization;


    //Setters and Getters for the fields of the class
    public Civilization getCivilization() {
        return civilization;
    }


    public void generateMap() {
        // TODO: 4/25/2022
    }

    public void endTurn() {
        // TODO: 4/25/2022
    }
}
