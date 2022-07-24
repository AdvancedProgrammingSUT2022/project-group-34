package app.views;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PublicChatroomController {
    @FXML
    private Button globalChatButton;

    @FXML
    private void initialize() {
        globalChatButton.setDisable(true);
    }

    @FXML
    private void goToPrivateChat() {
        App.setMenu("private_chatroom");
    }

    @FXML
    private void goToGroupChat() {
        App.setMenu("group_chatroom");
    }

    @FXML
    private void backToMainMenu() {
        App.setMenu("main_menu");
    }
}
