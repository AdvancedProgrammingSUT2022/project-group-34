package app.views;

import app.App;
import app.controllers.singletonController.UserController;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardController {
    @FXML
    private BorderPane pane;

    @FXML
    private GridPane table;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/leaderboard.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);

        setTable();
    }

    private void setTable() {
        setHeader();
        ArrayList<User> users = UserController.getInstance().getUsers();
        Collections.sort(users);
        for (int i = 0; i < 10; i++) {
            if (i >= users.size()) break;
            User user = users.get(i);
            addUser(user, i);
        }
        if (users.size() > 10) table.add(new Label("..."), 2, 11);
    }

    private void setHeader() {
        Label index = new Label("#");
        index.getStyleClass().add("index");
        Label avatar = new Label("");
        avatar.getStyleClass().add("avatar");
        Label name = new Label("Nickname");
        name.getStyleClass().add("name");
        Label score = new Label("Score");
        score.getStyleClass().add("score");
        Label lastWin = new Label("Last win");
        lastWin.getStyleClass().add("date");
        Label lastSeen = new Label("Last seen");
        lastSeen.getStyleClass().add("date");
        index.getStyleClass().add("header");
        avatar.getStyleClass().add("header");
        name.getStyleClass().add("header");
        score.getStyleClass().add("header");
        lastWin.getStyleClass().add("header");
        lastSeen.getStyleClass().add("header");
        table.add(index, 0, 0);
        table.add(avatar, 1, 0);
        table.add(name, 2, 0);
        table.add(score, 3, 0);
        table.add(lastWin, 4, 0);
        table.add(lastSeen, 5, 0);
    }

    private void addUser(User user, int i) {
        //if (user.equals(UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()))) user.setLastSeen(System.currentTimeMillis());
        Label index = new Label(String.valueOf(i + 1));
        index.getStyleClass().add("index");
        ImageView avatar = user.getImageView();
        avatar.getStyleClass().add("avatar");
        avatar.setFitHeight(45);
        avatar.setFitWidth(45);
        Label name = new Label(user.getNickname());
        name.getStyleClass().add("name");
        Label score = new Label(String.valueOf(user.getScore()));
        score.getStyleClass().add("score");
        String date = "";
        try {
            date = user.getScoreTime().toString();
        }
        catch (Exception e) {
            date = "N/A";
        }
        Label lastWin = new Label(date);
        lastWin.getStyleClass().add("date");
        Label lastSeen = new Label(user.getLastSeen().toString());
        lastSeen.getStyleClass().add("date");
        String styleClass = "";
        index.getStyleClass().add(styleClass);
        avatar.getStyleClass().add(styleClass);
        name.getStyleClass().add(styleClass);
        score.getStyleClass().add(styleClass);
        lastWin.getStyleClass().add(styleClass);
        lastSeen.getStyleClass().add(styleClass);
        table.add(index, 0, i + 1);
        table.add(avatar, 1, i + 1);
        table.add(name, 2, i + 1);
        table.add(score, 3, i + 1);
        table.add(lastWin, 4, i + 1);
        table.add(lastSeen, 5, i + 1);
    }

    @FXML
    private void exit() {
        App.setMenu("main_menu");
    }
}
