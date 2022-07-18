package app.views;

import app.App;
import app.controllers.GameController;
import app.controllers.UserController;
import app.models.Game;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameMenuController {
    @FXML
    private ChoiceBox<Integer> numberOfAutoSaveFiles;
    @FXML
    private ChoiceBox<Integer> frequencyOfSaving;
    @FXML
    private ChoiceBox<Integer> numberOfPlayers;
    @FXML
    private ChoiceBox<String> sizeOfTheMap;
    @FXML
    private Button resumeButton;
    @FXML
    private TextField invitationTextField;
    @FXML
    private Button invitationButton;
    @FXML
    private Text invitationError;
    @FXML
    private TextField startCommand;
    @FXML
    private Text startGameError;

    @FXML
    private void initialize() {
        setNumberOfAutoSaveFiles();
        setFrequencyOfSaving();
        setNumberOfPlayers();
        setSizeOfTheMap();
        if (GameController.getInstance().getGame() == null)
            resumeButton.setDisable(true);
        if (GameController.getInstance().getGame()!=null){
            invitationTextField.setDisable(true);
            invitationButton.setDisable(true);
        }
    }

    private void setNumberOfAutoSaveFiles() {
        for (int i = 2; i < 6; i++)
            numberOfAutoSaveFiles.getItems().add(i);
        // TODO: Field in save class
        numberOfAutoSaveFiles.setValue(2);
    }

    private void setFrequencyOfSaving() {
        for (int i = 2; i < 6; i++)
            frequencyOfSaving.getItems().add(i);
        // TODO: Field in save class
        frequencyOfSaving.setValue(5);
    }

    private void setNumberOfPlayers() {
        for (int i = 3; i < 8; i++)
            numberOfPlayers.getItems().add(i);
        if (GameController.getInstance().getGame() != null) {
            numberOfPlayers.setValue(GameController.getInstance().getGame().getUsers().size());
            numberOfPlayers.setDisable(true);
        } else
            numberOfPlayers.setValue(4);
    }

    private void setSizeOfTheMap() {
        sizeOfTheMap.getItems().add("Small");
        sizeOfTheMap.getItems().add("Medium");
        sizeOfTheMap.getItems().add("Large");
        sizeOfTheMap.getItems().add("Extra Large");

        if (GameController.getInstance().getGame() != null) {
            Game game = GameController.getInstance().getGame();
            switch (game.getMainGameMap().getMapHeight() / game.getUsers().size()) {
                case 12:
                    sizeOfTheMap.setValue("Small");
                    break;
                case 13:
                    sizeOfTheMap.setValue("Medium");
                    break;
                case 14:
                    sizeOfTheMap.setValue("Large");
                    break;
                case 15:
                    sizeOfTheMap.setValue("Extra Large");
                    break;
            }
            sizeOfTheMap.setDisable(true);
        } else
            sizeOfTheMap.setValue("Medium");
    }

    @FXML
    private void startNewGame() {
        startGameError.setFill(Color.rgb(250, 0, 0));
        if (startCommand.getText().isEmpty()) {
            startGameError.setText("Please enter some other usernames to start the game!");
            return;
        }
        String[] usernames = startCommand.getText().split("/");
        if (usernames.length != numberOfPlayers.getValue()) {
            startGameError.setText("Wrong number of usernames");
            return;
        }
        ArrayList<User> users = new ArrayList<>();
        for (String username : usernames) {
            User user;
            if ((user = UserController.getInstance().getUserByUsername(username)) == null)
                startGameError.setText("Invalid username");
            else if (users.contains(user))
                startGameError.setText("Some usernames are duplicate");
            else
                users.add(user);
        }
        // TODO: start game
    }

    @FXML
    private void backToMainMenu() {
        GameController.getInstance().setGame(null);
        App.setMenu("main_menu");
    }

    @FXML
    private void sendInvitation() {
        invitationError.setFill(Color.rgb(250, 0, 0));
        if (invitationTextField.getText().isEmpty())
            invitationError.setText("Field is empty!");
        else if (UserController.getInstance().getUserByUsername(invitationTextField.getText()) == null)
            invitationError.setText("Username doesn't exist!");
        else {
            // TODO: Phase 3
            invitationError.setText("Invitation sent!");
        }
    }
}
