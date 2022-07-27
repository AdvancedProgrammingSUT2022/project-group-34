package app.controllers;

import app.models.Civilization;
import app.models.Game;
import app.models.connection.PreGame;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.graphicalMenu.MenuController;
import com.google.gson.Gson;

import java.util.ArrayList;

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

    public static void setInstance(GameController gameController) {
        instance = gameController;
    }


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

    public void startTurn() {

    }

    public ArrayList<PreGame> getAllPreGame() {
        Message message;
        Processor processor = new Processor("GameController","get","allPreGames");
        processor.setGetOrSet(true);
        MenuController.sendProcessor(processor);
        message = InputController.getInstance().getMessage();
        Object objects  = message.getData("allPreGames");
        ArrayList<PreGame> userArray = new ArrayList<>();
        for (var object : ((ArrayList)objects)) {
            userArray.add(new Gson().fromJson((String) object, PreGame.class));
        }
        return userArray;
    }
}
