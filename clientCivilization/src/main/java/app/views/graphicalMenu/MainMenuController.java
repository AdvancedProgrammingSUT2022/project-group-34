package app.views.graphicalMenu;

import app.App;
import app.models.connection.Processor;
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
        Processor processor = new Processor("user logout");
        MenuController.sendProcessorAndIgnoreResponse(processor);
        App.setMenu("login_menu");
    }

    @FXML
    private void profile() {
        Processor processor = new Processor("menu enter profile");
        MenuController.sendProcessorAndIgnoreResponse(processor);
        App.setMenu("profile_menu");
    }

    @FXML
    private void leaderboard() {
        App.setMenu("leaderboard");
    }

    @FXML
<<<<<<< HEAD:clientCivilization/src/main/java/app/views/graphicalMenu/MainMenuController.java
=======
    private void lobby() {
        App.setMenu("lobby");
    }

>>>>>>> c63170a108145fccc0a922b7f5b0335e11a5567d:civilization/src/main/java/app/views/MainMenuController.java
    public void gameMenu() {
        App.setMenu("game_menu");
    }

    @FXML
    private void chatroom() {
        App.setMenu("public_chatroom");
    }
}
