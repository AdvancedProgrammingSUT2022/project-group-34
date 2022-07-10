package app.views;

import app.App;
import app.controllers.UserController;
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
        UserController.getInstance().setLoggedInUser(null);
        App.setMenu("login_menu");
    }

    @FXML
    private void profile() {
        App.setMenu("profile_menu");
    }
}
