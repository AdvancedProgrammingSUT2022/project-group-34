package app.models.miniClass;

import app.controllers.singletonController.GMini;
import app.models.Civilization;
import app.models.Game;

import java.util.ArrayList;

public class MiniGame {
    private ArrayList<Integer> miniCivilizationsID = new ArrayList<>();
    private Integer mainGameMapID;

    public MiniGame(Game game) {
        ArrayList<Civilization> civilizations = game.getCivilizations();
        civilizations.forEach(civilization -> miniCivilizationsID.add(GMini.getInstance().miniSave(civilization)));
        this.mainGameMapID = GMini.getInstance().miniSave(game.getMainGameMap());
    }
}
