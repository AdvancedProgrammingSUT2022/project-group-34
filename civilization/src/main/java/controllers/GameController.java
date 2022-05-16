package controllers;

import models.Civilization;
import models.Game;
import models.User;
import models.map.CivilizationMap;
import models.map.GameMap;

import java.util.ArrayList;

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
    private Game game;
    private int civilizationIndex;
    private Civilization civilization;


    //Setters and Getters for the fields of the class
    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void startNewGame(ArrayList<User> users) {
        game = new Game(users);
        GameMap mainGameMap = game.getMainGameMap();
        for (Civilization civilization : game.getCivilizations()) {
            civilization.setPersonalMap(new CivilizationMap(mainGameMap.getMapWidth(), mainGameMap.getMapHeight(), mainGameMap));
            CivilizationController.getInstance().updateTransparentTiles(civilization);
            CivilizationController.getInstance().updatePersonalMap(civilization, mainGameMap);
        }
        this.civilization = game.getCivilizations().get(0);
        this.civilizationIndex = 0;
    }


    public int getIndex(Civilization civilization) {
        return game.getCivilizations().indexOf(civilization);
    }


    public void generateMap() {
        // TODO: 4/25/2022
    }

    public void startTurn() {
        for (Civilization civilization : game.getCivilizations()) {
            CivilizationController.getInstance().updateCivilization(civilization);
        }
        civilizationIndex++;
        civilizationIndex %= game.getCivilizations().size();
        civilization = game.getCivilizations().get(civilizationIndex);
        civilization.setTurn(civilization.getTurn() + 1)
    }
}
