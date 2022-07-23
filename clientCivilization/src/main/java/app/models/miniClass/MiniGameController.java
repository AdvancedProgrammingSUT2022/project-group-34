package app.models.miniClass;

import app.controllers.GLoad;
import app.controllers.GameController;
import app.models.Civilization;
import app.models.Game;

public class MiniGameController extends Mini{
    private Integer miniGameID;
    private Integer civilizationID;


    @Override
    public GameController getOriginal() {
        GameController gameController = GameController.getInstance();

        gameController.setGame((Game) GLoad.getInstance().load(new MiniGame(), miniGameID));
        gameController.setCivilization((Civilization) GLoad.getInstance().load(new MiniCivilization(), civilizationID));
        return gameController;
    }
}
