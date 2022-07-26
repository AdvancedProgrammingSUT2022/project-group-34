package app.controllers.gameServer;

import app.controllers.singletonController.GSave;
import app.controllers.MainServer;
import app.models.Civilization;
import app.models.Game;
import app.models.User;
import app.models.map.CivilizationMap;
import app.models.map.GameMap;

import java.util.ArrayList;

public class GameController {


    public static GameController getInstance() {
        return null;
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
        startNewGame(users, 1);
    }
    public void startNewGame(ArrayList<User> users, int mapScale) {
        game = new Game(users, mapScale);
        GameMap mainGameMap = game.getMainGameMap();
        for (Civilization civilization : game.getCivilizations()) {
            civilization.setPersonalMap(new CivilizationMap(mainGameMap.getMapWidth(), mainGameMap.getMapHeight(), mainGameMap));
            MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).updateTransparentTiles(civilization);
            MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).updatePersonalMap(civilization, mainGameMap);
        }
        this.civilization = game.getCivilizations().get(0);
        this.civilizationIndex = 0;
        GSave.getInstance().saveAllGame(this);
        //game = GLoad.getInstance().loadAllGame(token);
    }


    public int getIndex(Civilization civilization) {
        return game.getCivilizations().indexOf(civilization);
    }


    public void generateMap() {
        // TODO: 4/25/2022
    }

    public void startTurn() {
        for (Civilization civilization : game.getCivilizations()) {
            MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).updateCivilization(civilization);
        }
        game.getCivilizations().get(civilizationIndex).setTurn(game.getCivilizations().get(civilizationIndex).getTurn()+1);
        civilizationIndex++;
        civilizationIndex %= game.getCivilizations().size();
        civilization = game.getCivilizations().get(civilizationIndex);
//        civilization.setTurn(civilization.getTurn() + 1);
    }
}
