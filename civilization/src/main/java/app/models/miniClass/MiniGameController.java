package app.models.miniClass;

import app.controllers.GMini;
import app.controllers.GameController;

public class MiniGameController {
    private Integer miniGameID;
    private Integer civilizationID;

    public MiniGameController(GameController gameController) {
        this.miniGameID = GMini.getInstance().miniSave(gameController.getGame());
        this.civilizationID = GMini.getInstance().miniSave(gameController.getCivilization());
    }
}
