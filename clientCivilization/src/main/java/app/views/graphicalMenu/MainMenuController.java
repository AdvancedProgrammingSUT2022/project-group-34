package app.views.graphicalMenu;

import app.App;
import app.controllers.InputController;
import app.controllers.UserController;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.commandLineMenu.Menu;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuController {
    @FXML
    private Pane pane;

    @FXML
    private void initialize () {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/main_menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT));
        pane.setBackground(background);
    }

    @FXML
    private void exit() {
        App.exit();
    }

    @FXML
    private void logout() {
        Processor processor = new Processor("menu exit");
        Menu.sendProcessor(processor);
        App.setMenu("login_menu");
    }

    @FXML
    private void profile() {
        App.setMenu("profile_menu");
    }

    @FXML
    private void leaderboard() {
        App.setMenu("leaderboard");
    }

    public void gameMenu() {
        App.setMenu("game_menu");
    }

    @FXML
    private void chatroom() {
        App.setMenu("public_chatroom");
    }
}
