package app.controllers;

import app.controllers.gameServer.CheatController;
import app.controllers.gameServer.CivilizationController;
import app.controllers.gameServer.DiplomacyController;
import app.controllers.gameServer.GameController;
import app.models.connection.StringGameToken;
import app.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainServer {

    private static HashMap<String, GameController> allGameControllers = new HashMap<>();
    private static HashMap<String, CivilizationController> allCivilizationControllers = new HashMap<>();
    private static HashMap<String, CheatController> allCheatControllers = new HashMap<>();
    private static HashMap<String, DiplomacyController> allDiplomacyControllers = new HashMap<>();


    public static void addGameController(String token, GameController gameController){
        allGameControllers.put(token, gameController);
    }
    public static void addCivilizationController(String token, CivilizationController civilizationController){
        allCivilizationControllers.put(token, civilizationController);
    }
    public static void addCheatController(String token, CheatController cheatController){
        allCheatControllers.put(token, cheatController);
    }
    public static void addDiplomacyController(String token, DiplomacyController diplomacyController){
        allDiplomacyControllers.put(token, diplomacyController);
    }


    public static GameController getGameControllerByToken(StringGameToken token){
        return allGameControllers.get(token.token);
    }
    public static CivilizationController getCivilizationControllerByToken(StringGameToken token){
        return allCivilizationControllers.get(token.token);
    }
    public static CheatController getCheatControllerByToken(StringGameToken token){
        return allCheatControllers.get(token.token);
    }
    public static DiplomacyController getDiplomacyControllerByToken(StringGameToken token){
        return allDiplomacyControllers.get(token.token);
    }



    public static StringGameToken getToken(CivilizationController civilizationController){
        final String[] token1 = new String[1];
        allCivilizationControllers.forEach((token, civilizationController2) -> {
            if (civilizationController2.equals(civilizationController)) {
                token1[0] = token;
            }
        });
        return new StringGameToken(token1[0]);
    }
    public static StringGameToken getToken(GameController gameController){
        final String[] token1 = new String[1];
        allGameControllers.forEach((token, gameController2) -> {
            if (gameController2.equals(gameController)) {
                token1[0] = token;
            }
        });
        return new StringGameToken(token1[0]);
    }
    public static StringGameToken getToken(CheatController cheatController){
        final String[] token1 = new String[1];
        allCheatControllers.forEach((token, cheatController1) -> {
            if (cheatController1.equals(cheatController)) {
                token1[0] = token;
            }
        });
        return new StringGameToken(token1[0]);
    }
    public static StringGameToken getToken(DiplomacyController diplomacyController){
        final String[] token1 = new String[1];
        allDiplomacyControllers.forEach((token, diplomacyController1) -> {
            if (diplomacyController1.equals(diplomacyController)) {
                token1[0] = token;
            }
        });
        return new StringGameToken(token1[0]);
    }

    public static void startNewGame(ArrayList<User> users, int mapScale) {
        String gameToken = UUID.randomUUID().toString();
        GameController gameController =  new GameController();
        gameController.startNewGame(users, mapScale);
        allGameControllers.put(gameToken, gameController);
        allCivilizationControllers.put(gameToken, new CivilizationController());
        allDiplomacyControllers.put(gameToken, new DiplomacyController());
        allCheatControllers.put(gameToken, new CheatController());
    }
}
