package app.views;

import app.controllers.gameServer.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
        if (GameController.getInstance().getGame() == null)
            resumeButton.setDisable(true);
        if (GameController.getInstance().getGame() != null) {
            invitationTextField.setDisable(true);
            invitationButton.setDisable(true);
        }
    }
}
