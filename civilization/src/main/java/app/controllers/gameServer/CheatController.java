//besmellah

package app.controllers.gameServer;

import app.controllers.MainServer;
import app.models.Civilization;
import app.models.tile.Tile;
import app.models.unit.Unit;

public class CheatController {
    private static CheatController instance = null;

    public static CheatController getInstance() {
        if (instance == null) instance = new CheatController();
        return instance;
    }

    public void increaseGold(int amount) {
        Civilization civilization = MainServer.getGameControllerByToken(MainServer.getToken(this)).getCivilization();
        civilization.setGold(civilization.getGold() + amount);
    }

    public void increaseBeaker(int amount) {
        Civilization civilization = MainServer.getGameControllerByToken(MainServer.getToken(this)).getCivilization();
        civilization.setNumberOfBeakers(civilization.getNumberOfBeakers() + amount);
    }

    public String teleport(Unit unit, int x, int y) {
        String check = MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).isMoveValid(unit, new int[]{x, y});
        if (check.equals("invalid destination") || check.equals("destination occupied")) return check;
        Tile destination = MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).getTileByPosition(new int[]{x, y});
        reveal(x, y);
        //System.out.println(unit.getPosition().getNonCombatUnit());
        MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).forcedMove(unit, destination);
        return "done";
    }

    public String reveal(int x, int y) {
        if (!MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).isPositionValid(new int[]{x, y})) return "invalid position";
        MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).reveal(x, y);
        return "done";
    }

    public String finishResearch() {
        Civilization civilization = MainServer.getGameControllerByToken(MainServer.getToken(this)).getCivilization();
        return MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).finishResearch(civilization);
    }

    public String researchTechnology(String name) {
        Civilization civilization = MainServer.getGameControllerByToken(MainServer.getToken(this)).getCivilization();
        int flag = MainServer.getCivilizationControllerByToken(MainServer.getToken(this)).researchTechnology(civilization, name);
        switch (flag){
            case -1:
                return "There is no technology with this name";
            case -2:
                return "This technology has been researched";
            default:
                return "done";
        }

    }

}