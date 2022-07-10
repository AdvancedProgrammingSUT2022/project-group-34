package app.views;

import app.controllers.UserController;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ProfileMenuController {
    @FXML
    private BorderPane pane;

    @FXML
    private VBox mainBox;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image("/app/background/login_menu.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
        User user = UserController.getInstance().getLoggedInUser();
        ImageView avatar = user.getImageView();
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(200);
        mainBox.getChildren().add(0, avatar);
    }
}
