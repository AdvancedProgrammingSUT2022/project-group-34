package app.controllers;

import app.models.Civilization;
import app.models.Game;

public class GameController {
    //Singleton Pattern
    private static GameController instance;

    public GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }


    //Fields of the class
    private Game game;
    private Civilization civilization;


    //Setters and Getters for the fields of the class
    public Game getGame() {
        return game;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public int getIndex(Civilization civilization) {
        return game.getCivilizations().indexOf(civilization);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
}
