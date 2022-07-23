package app.models.miniClass;

import app.controllers.GLoad;
import app.models.Civilization;
import app.models.Game;
import app.models.map.GameMap;

import java.util.ArrayList;

public class MiniGame extends Mini{
    private ArrayList<Integer> miniCivilizationsID = new ArrayList<>();
    private Integer mainGameMapID;

    @Override
    public Game getOriginal() {
        Game game = new Game();
        game.setMainGameMap((GameMap) GLoad.getInstance().load(new MiniGameMap(), mainGameMapID));
        miniCivilizationsID.forEach(id -> game.getCivilizations().add((Civilization) GLoad.getInstance().load(new MiniCivilization(), id)));
        return game;
    }
}
