package app.views.graphicalMenu;

import app.App;
import app.controllers.InputController;
import app.controllers.UserController;
import app.models.User;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.Adapter;
import app.views.commandLineMenu.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

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
    private Button chooserButton;

    private FileChooser fileChooser;
    private File selectedFile;

    @FXML
    private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.setTitle("Pick a profile picture...");
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/login_menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
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
    }

    @FXML
    private void register() {
        clearMessage();
        String username = registerUsername.getText();
        String password = registerPassword.getText();
        String nickname = this.nickname.getText();
        Processor processor = new Processor(Adapter.register(username,nickname,password));
        Menu.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        //-----------
        registerResponse(message);
    }

    private void registerResponse(Message message) {
        String logString = (String) message.getData("loginMessage");
        if (message.isSuccessful()) {
            registerMessage.setText(logString);
            registerMessage.setStyle("-fx-text-fill: red;");
            try {
                //UserController.getInstance().getUsers().add(new User(username, password, nickname, selectedFile)); todo server
            } catch (Exception e) {
                registerMessage.setText(logString);//registerMessage.setText("Unable to load picture!");
                registerMessage.setStyle("-fx-text-fill: red;");
                selectedFile = null;
                e.printStackTrace();
                return;
            }

            registerMessage.setText(logString);
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
        Processor processor = new Processor(Adapter.login(username,password));
        Menu.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        loginResponse(message);
    }

    private void loginResponse(Message message) {
        String logString = (String) message.getData("loginMessage");
        String logStyle = "-fx-text-fill: red;";
        if (logString.length() != 0) {
            loginMessage.setText(logString);
            loginMessage.setStyle(logStyle);
        }
        if (message.getCurrentMenu() != null && !message.getCurrentMenu().equals("login"))
            App.setMenu(message.getCurrentMenu() + "_menu");

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
