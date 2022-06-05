package app.views;

import app.App;
import app.controllers.UserController;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class LoginMenuController {
    private final static String VALID_USERNAME_REGEX = "^[a-zA-Z][a-zA-Z\\d]*$";
    private final static String VALID_NICKNAME_REGEX = "^[a-zA-Z]+$";

    @FXML
    private BorderPane pane;

    @FXML
    private Label registerMessage;

    @FXML
    private Label loginMessage;

    @FXML
    private TextField registerUsername;

    @FXML
    private TextField loginUsername;

    @FXML
    private TextField nickname;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/login_menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
    }

    @FXML
    private void register() {
        clearMessage();
        String username = registerUsername.getText();
        String password = registerPassword.getText();
        String nickname = this.nickname.getText();

        if (!username.matches(VALID_USERNAME_REGEX)) {
            registerMessage.setText("Invalid username!");
            registerMessage.setStyle("-fx-text-fill: red;");
        }
        else if (!nickname.matches(VALID_USERNAME_REGEX)) {
            registerMessage.setText("Invalid nickname!");
            registerMessage.setStyle("-fx-text-fill: red;");
        }
        else if (UserController.getInstance().getUserByUsername(username) != null) {
            registerMessage.setText("A user with username " + username + " already exists");
            registerMessage.setStyle("-fx-text-fill: red;");
        }
        else if (UserController.getInstance().getUserByNickname(nickname) != null) {
            registerMessage.setText("A user with nickname " + nickname + " already exists");
            registerMessage.setStyle("-fx-text-fill: red;");
        }
        else if (!UserController.getInstance().isPasswordStrong(password)) {
            registerMessage.setText("Password is weak!");
            registerMessage.setStyle("-fx-text-fill: red;");
        }
        else {
            UserController.getInstance().getUsers().add(new User(username, password, nickname));
            registerMessage.setText("User Created successfully!");
            registerMessage.setStyle("-fx-text-fill: green;");
            registerUsername.setText("");
            registerPassword.setText("");
            this.nickname.setText("");
        }
    }

    @FXML
    private void login() {
        clearMessage();
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        User user = UserController.getInstance().getUserByUsername(username);
        if (user == null || !user.isPasswordCorrect(password)) {
            loginMessage.setText("Username or password didn't match!");
            loginMessage.setStyle("-fx-text-fill: red;");
        }
        else {
            UserController.getInstance().setLoggedInUser(user);
            App.setMenu("main_menu");
        }
    }

    @FXML
    private void clearMessage() {
        registerMessage.setText("");
        loginMessage.setText("");
    }

    @FXML
    private void exit() {
        App.exit();
    }
}
