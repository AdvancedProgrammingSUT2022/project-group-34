//besmellah

package controllers;

import models.Civilization;
import models.tile.Tile;
import models.unit.Unit;

public class CheatController {
    private static CheatController instance = null;

    public static CheatController getInstance() {
        if (instance == null) instance = new CheatController();
        return instance;
    }

    public void increaseGold(int amount) {
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.setGold(civilization.getGold() + amount);
    }

    public void increaseBeaker(int amount) {
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.setNumberOfBeakers(civilization.getNumberOfBeakers() + amount);
    }

    public String teleport(Unit unit, int x, int y) {
        String check = CivilizationController.getInstance().isMoveValid(unit, new int[]{x, y});
        if (check.equals("invalid destination") || check.equals("destination occupied")) return check;
        Tile destination = CivilizationController.getInstance().getTileByPosition(new int[]{x, y});
        reveal(x, y);
        //System.out.println(unit.getPosition().getNonCombatUnit());
        CivilizationController.getInstance().forcedMove(unit, destination);
        return "done";
    }

    public String reveal(int x, int y) {
        if (!CivilizationController.getInstance().isPositionValid(new int[]{x, y})) return "invalid position";
        CivilizationController.getInstance().reveal(x, y);
        return "done";
    }

    public String finishResearch() {
        //TODO
        return "TODO";
    }
}