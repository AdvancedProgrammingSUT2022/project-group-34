package app.views.graphicalMenu;

import app.App;
import app.controllers.GameController;
import app.controllers.InputController;
import app.controllers.UserController;
import app.models.connection.Message;
import app.models.connection.PreGame;
import app.models.connection.Processor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class LobbyController {

    public Label serverMessage;
    @FXML
    private TextField numberOfPlayer;

    @FXML
    private BorderPane pane;

    @FXML
    private GridPane table;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/lobby.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);
        setTable();
    }

    private void setTable() {
        table.getChildren().clear();
        setHeader();
        ArrayList<PreGame> allPreGame = GameController.getInstance().getAllPreGame(); //TODO...
        System.out.println(allPreGame);
        for (int i = 0; i < 10; i++) {
            if (i >= allPreGame.size()) break;
            PreGame preGame = allPreGame.get(i);
            if (preGame != null)
                addPreGame(preGame, i);
        }
        if (allPreGame.size() > 10) table.add(new Label("..."), 2, 11);
    }

    private void setHeader() {
        Label index = new Label("#");
        index.getStyleClass().add("index");
        Label capacity = new Label("Capacity");
        capacity.getStyleClass().add("capacity");
        Label users = new Label("Users");
        users.getStyleClass().add("users");
        Label join = new Label(" ");
        join.getStyleClass().add("button");
        Label leave = new Label(" ");
        Label start = new Label(" ");
        leave.getStyleClass().add("button");
        start.getStyleClass().add("button");
        index.getStyleClass().add("header");
        capacity.getStyleClass().add("header");
        users.getStyleClass().add("header");
        join.getStyleClass().add("header");
        leave.getStyleClass().add("header");
        start.getStyleClass().add("header");
        table.add(index, 0, 0);
        table.add(capacity, 1, 0);
        table.add(users, 2, 0);
        table.add(join, 3, 0);
        table.add(leave, 4, 0);
        table.add(start, 4, 0);
    }

    private void addPreGame(PreGame preGame, int i) { //TODO... user?
        Label index = new Label(String.valueOf(i + 1));
        index.getStyleClass().add("index");
        Label capacity = new Label(String.valueOf(preGame.getCapacity()));
        capacity.getStyleClass().add("capacity");
        System.out.println("addPreGame ->" + preGame.getUsers());
        Label users = new Label(preGame.getUsers().toString());
        users.getStyleClass().add("users");

        Button joinButton = new Button("Join Game");
        joinButton.getStyleClass().add("button");
        Button leaveButton = new Button("Leave Game");
        leaveButton.getStyleClass().add("button");
        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("button");
        String styleClass = "";
        if (preGame.equals(UserController.getInstance().getLoggedInUser())) styleClass = "self";
        else if (i % 2 == 1) styleClass = "odd";
        else styleClass = "even";
        index.getStyleClass().add(styleClass);
        capacity.getStyleClass().add(styleClass);
        users.getStyleClass().add(styleClass);

        joinButton.getStyleClass().add(styleClass);
        leaveButton.getStyleClass().add(styleClass);
        startButton.getStyleClass().add(styleClass);

        joinButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                join(i);
            }
        });
        leaveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                leave(i);
            }
        });
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                start(i);
            }
        });
        table.add(index, 0, i + 1);
        table.add(capacity, 1, i + 1);
        table.add(users, 2, i + 1);
        table.add(joinButton, 3, i + 1);
        table.add(leaveButton, 4, i + 1);
        table.add(startButton, 5, i + 1);
    }

    private void start(int index) {
        Processor processor = new Processor("play","play","");
        processor.addField("index", String.valueOf(index));
        MenuController.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        startResponse(message);
    }

    private void startResponse(Message message) {
        if (message.isSuccessful()){
            serverMessage.setStyle("-fx-text-fill: green;");
            serverMessage.setText(message.getMessageString());
            App.setMenu("game_view");
        }
        else {
            serverMessage.setStyle("-fx-text-fill: red;");
            serverMessage.setText(message.getMessageString());
        }
    }

    @FXML
    private void exit() {
        App.setMenu("main_menu");
    }

    private void join(int index) {
        Processor processor = new Processor("play","join","");
        processor.addField("index", String.valueOf(index));
        System.out.println(processor);
        MenuController.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        joinLeaveResponse(message);
    }

    private void leave(int index) {
        Processor processor = new Processor("play","leave","");
        processor.addField("index", String.valueOf(index));
        System.out.println(processor);
        MenuController.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        joinLeaveResponse(message);
    }

    private void joinLeaveResponse(Message message) {
        if (message.isSuccessful()){
            serverMessage.setStyle("-fx-text-fill: green;");
            serverMessage.setText(message.getMessageString());
            setTable();
        }
        else {
            serverMessage.setStyle("-fx-text-fill: red;");
            serverMessage.setText(message.getMessageString());
        }
    }

    private void success(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }


    public void create() {
        String capacity = numberOfPlayer.getText();
        int n = -1;
        try {
            n = Integer.parseInt(capacity);
        } catch (Exception e){};

        Processor processor = new Processor("play","create","");
        processor.addField("capacity", String.valueOf(n));
        System.out.println(processor);
        MenuController.sendProcessor(processor);
        Message message = InputController.getInstance().getMessage();
        createResponse(message);
    }

    private void createResponse(Message message) {
        if (message.isSuccessful()){
            serverMessage.setStyle("-fx-text-fill: green;");
            serverMessage.setText(message.getMessageString());
            setTable();
        }
        else {
            serverMessage.setStyle("-fx-text-fill: red;");
            serverMessage.setText(message.getMessageString());
        }
    }

    public void clearMessage(KeyEvent keyEvent) {
    }
}
