package app.views;

import app.App;
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
}
