package app.views;

import app.App;
import app.controllers.UserController;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfileMenuController {
    @FXML
    private BorderPane pane;

    @FXML
    private VBox mainBox;
    
    @FXML
    private Button chooserButton;

    @FXML
    private Label message;

    private FileChooser fileChooser;
    private File selectedFile;

    @FXML
    private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.setTitle("Pick a profile picture...");
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/profile_menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
        User user = UserController.getInstance().getLoggedInUser();
        loadAvatar(user);
        loadName(user);
    }

    private void loadAvatar(User user) {
        ImageView avatar = user.getImageView();
        System.out.println(avatar.getImage().getUrl());
        System.out.println(avatar.getImage().getHeight());
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(120);
        System.out.println(avatar.getFitHeight());
        mainBox.getChildren().set(0, avatar);
        System.out.println(avatar.getX() + " " + avatar.getY() + " " + avatar.getLayoutX() + " " + avatar.getOpacity() + " " + avatar.getScaleX());
    }

    private void loadName(User user) {
        Label name = new Label();
        name.setText(user.getUsername() + " (" + user.getNickname() + ")");
        name.getStyleClass().add("title");
        mainBox.getChildren().set(1, name);
    }

    @FXML
    private void openFileChooser() {
        if (selectedFile == null) selectedFile = fileChooser.showOpenDialog(App.getStage());
        else selectedFile = null;
        if (selectedFile == null) {
            chooserButton.setId("choose");
            chooserButton.setText("Pick a file...");
        }
        else {
            chooserButton.setId("chosen");
            chooserButton.setText("Remove picture");
        }

        try {
            message.setText("");
            UserController.getInstance().getLoggedInUser().setAvatar(selectedFile);
        } catch (Exception e) {
            message.setText("Unable to load picture.");
        }
        loadAvatar(UserController.getInstance().getLoggedInUser());
    }
}
