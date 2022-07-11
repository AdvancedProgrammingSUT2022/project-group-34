package app.views;

import app.App;
import app.controllers.UserController;
import app.models.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Button remover;

    @FXML
    private Label message;

    @FXML
    private HBox placeholderBox;

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
        loadPlaceholders();
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
        name.getStyleClass().add("header");
        mainBox.getChildren().set(1, name);
    }

    private void loadPlaceholders() {
        for (int i = 0; i < 5; i++) {
            addPlaceholder();
        }
    }

    private void addPlaceholder() {
        File file = new File("src/main/resources/app/placeholders/" + placeholderBox.getChildren().size() + ".png");
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(55);
        imageView.getStyleClass().add("placeholder");
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setAvatar(file);
            }
        });
        placeholderBox.getChildren().add(imageView);
    }

    @FXML
    private void openFileChooser() {
        selectedFile = fileChooser.showOpenDialog(App.getStage());
        setAvatar(selectedFile);
    }

    @FXML
    private void removeAvatar() {
        setAvatar(null);
    }

    private void setAvatar(File file) {
        try {
            message.setText("");
            UserController.getInstance().getLoggedInUser().setAvatar(file);
        } catch (Exception e) {
            message.setText("Unable to load picture.");
        }
        loadAvatar(UserController.getInstance().getLoggedInUser());
    }
}
