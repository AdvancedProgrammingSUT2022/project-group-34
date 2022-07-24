package app.views;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GroupChatroomController {
    @FXML
    private Button groupChatButton;

    @FXML
    private void initialize() {
        groupChatButton.setDisable(true);
    }

    @FXML
    private void goToPublicChat() {
        App.setMenu("public_chatroom");
    }

    @FXML
    private void goToPrivateChat() {
        App.setMenu("private_chatroom");
    }

    @FXML
    private void backToMainMenu() {
        App.setMenu("main_menu");
    }
}
