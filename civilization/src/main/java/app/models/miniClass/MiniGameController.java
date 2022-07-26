package app.models.miniClass;

import app.controllers.singletonController.GMini;
import app.controllers.gameServer.GameController;

public class MiniGameController {
    private Integer miniGameID;
    private Integer civilizationID;

    public MiniGameController(GameController gameController) {
        this.miniGameID = GMini.getInstance().miniSave(gameController.getGame());
        this.civilizationID = GMini.getInstance().miniSave(gameController.getCivilization());
    }
}
