package app.views;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrivateChatroomController {
    @FXML
    private Button privateChatButton;

    @FXML
    private void initialize() {
        privateChatButton.setDisable(true);
    }

    @FXML
    private void goToPublicChat() {
        App.setMenu("public_chatroom");
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
