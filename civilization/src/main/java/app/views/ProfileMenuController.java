package app.views;

import app.App;
import app.controllers.UserController;
import app.models.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfileMenuController {
    private final static String VALID_NICKNAME_REGEX = "^[a-zA-Z]+$";

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

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField newNicknameField;

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
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(120);
        mainBox.getChildren().set(0, avatar);
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

    private void showFailureMessage(String string) {
        message.setText(string);
        message.setStyle("-fx-text-fill: #ff2200;");
    }

    private void showSuccessMessage(String string) {
        message.setText(string);
        message.setStyle("-fx-text-fill: #00ff55;");
    }

    private void clearMessage() {
        message.setText("");
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
        clearMessage();
        try {
            UserController.getInstance().getLoggedInUser().setAvatar(file);
            showSuccessMessage("Avatar changed successfully.");
        } catch (Exception e) {
            showFailureMessage("Unable to load picture.");
        }
        loadAvatar(UserController.getInstance().getLoggedInUser());
    }

    @FXML
    private void changePassword() {
        String currentPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();

        clearMessage();

        if (!UserController.getInstance().getLoggedInUser().isPasswordCorrect(currentPassword)) {
            showFailureMessage("incorrect password");
        }
        else if (currentPassword.equals(newPassword)) {
            showFailureMessage("Insert a new password.");
        }
        else if (!UserController.getInstance().isPasswordStrong(newPassword)) {
            showFailureMessage("Password is weak!");
        }
        else {
            UserController.getInstance().getLoggedInUser().setPassword(newPassword);
            showSuccessMessage("Password changed successfully!");
            UserController.getInstance().saveUsers();
            oldPasswordField.setText("");
            newPasswordField.setText("");
        }
    }

    @FXML
    private void changeNickname() {
        String nickname = newNicknameField.getText();

        clearMessage();

        if (!nickname.matches(VALID_NICKNAME_REGEX))
            showFailureMessage("Please enter a valid nickname. (one or more alphabetic characters)");
        else if (UserController.getInstance().getLoggedInUser().getNickname().equals(nickname))
            showFailureMessage("Please enter a new nickname.");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            showFailureMessage("A user with nickname " + nickname + " already exists.");
        else {
            UserController.getInstance().getLoggedInUser().setNickname(nickname);
            showSuccessMessage("Nickname changed successfully!");
            UserController.getInstance().saveUsers();
            newNicknameField.setText("");
        }
        loadName(UserController.getInstance().getLoggedInUser());

    }

    @FXML
    private void exit() {
        App.setMenu("main_menu");
    }
}