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
                new Image(getClass().getResource("/app/background/profile_menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
        User user = UserController.getInstance().getLoggedInUser();
        ImageView avatar = user.getImageView();
        System.out.println(avatar.getImage().getUrl());
        System.out.println(avatar.getImage().getHeight());
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(120);
        System.out.println(avatar.getFitHeight());
        mainBox.getChildren().add(0, avatar);
        System.out.println(avatar.getX() + " " + avatar.getY() + " " + avatar.getLayoutX() + " " + avatar.getOpacity() + " " + avatar.getScaleX());
    }
}
