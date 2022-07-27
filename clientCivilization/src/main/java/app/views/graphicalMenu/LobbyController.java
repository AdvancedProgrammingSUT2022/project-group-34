package app.views.graphicalMenu;

import app.App;
import app.controllers.GameController;
import app.controllers.UserController;
import app.models.PreGame;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class LobbyController {
    @FXML
    private Label serverMessage;

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
        for (int i = 0; i < 10; i++) {
            if (i >= allPreGame.size()) break;
            PreGame preGame = allPreGame.get(i);
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
        leave.getStyleClass().add("button");
        index.getStyleClass().add("header");
        capacity.getStyleClass().add("header");
        users.getStyleClass().add("header");
        join.getStyleClass().add("header");
        leave.getStyleClass().add("header");
        table.add(index, 0, 0);
        table.add(capacity, 1, 0);
        table.add(users, 2, 0);
        table.add(join, 3, 0);
        table.add(leave, 4, 0);
    }

    private void addPreGame(PreGame preGame, int i) { //TODO... user?
        Label index = new Label(String.valueOf(i + 1));
        index.getStyleClass().add("index");
        Label capacity = new Label(String.valueOf(preGame.getCapacity()));
        capacity.getStyleClass().add("capacity");
        Label users = new Label(preGame.getUsers().toString());
        users.getStyleClass().add("users");
        Button joinButton = new Button("Join Game");
        joinButton.getStyleClass().add("button");
        Button leaveButton = new Button("Leave Game");
        leaveButton.getStyleClass().add("button");
        String styleClass = "";
        if (preGame.equals(UserController.getInstance().getLoggedInUser())) styleClass = "self";
        else if (i % 2 == 1) styleClass = "odd";
        else styleClass = "even";
        index.getStyleClass().add(styleClass);
        capacity.getStyleClass().add(styleClass);
        users.getStyleClass().add(styleClass);
        joinButton.getStyleClass().add(styleClass);
        leaveButton.getStyleClass().add(styleClass);
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
        table.add(index, 0, i + 1);
        table.add(capacity, 1, i + 1);
        table.add(users, 2, i + 1);
        table.add(joinButton, 3, i + 1);
        table.add(leaveButton, 4, i + 1);
    }

    @FXML
    private void exit() {
        App.setMenu("main_menu");
    }

    private void join(int index) {
        //TODO...
    }

    private void leave(int index) {
        //TODO...
    }

    private void success(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
