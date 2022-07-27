package app.views.graphicalMenu;

import app.App;
import app.controllers.ConnectionController;
import app.controllers.UserController;
import app.models.ChatDatabase;
import app.models.Communicator;
import app.models.connection.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PublicChatroomController {
    @FXML
    private VBox messagesList;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button globalChatButton;

    @FXML
    private void initialize() {
        ConnectionController.setController(this);
        ConnectionController.endListenerThread = false;
        ConnectionController.listenForUpdates();
        globalChatButton.setDisable(true);
        getChatMessages();
        showChatMessages();
    }

    private void getChatMessages() {
        Communicator request = new Communicator("getGlobalChatMessages");
        Communicator response = ConnectionController.send(request);
        String messageJson = new Gson().toJson(response.getData().get("messages"));
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
        ArrayList<ChatMessage> messages = gson.fromJson(messageJson, new TypeToken<ArrayList<ChatMessage>>() {
        }.getType());
        ChatDatabase.setGlobalChatMessages(messages);
    }

    public void showChatMessages() {
        messagesList.getChildren().clear();
        messagesList.setStyle("-fx-pref-width: 910");
        ArrayList<ChatMessage> messages = ChatDatabase.getGlobalChatMessages();
        for (ChatMessage message : messages) {
            String username = message.getSenderUsername();
            HBox holder = new HBox();

            VBox vBox = new VBox();

            Label sendersName = new Label(message.getSenderUsername());
            sendersName.setStyle("-fx-text-fill: #6dd4d5");
            Text text = new Text(message.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Label time = new Label(message.getSentAt().format(formatter));
            vBox.getChildren().add(sendersName);
            vBox.getChildren().add(text);
            vBox.getChildren().add(time);

            ImageView PFP = UserController.getInstance().getUserByUsername(username).getImageView();
            PFP.setFitHeight(45);
            PFP.setFitWidth(45);

            if (username.equals(UserController.getInstance().getLoggedInUser().getUsername())) {
                vBox.setStyle("-fx-background-color: #b27777; -fx-padding: 8; -fx-background-radius: 12; -fx-alignment: center-right");
                holder.setStyle("-fx-alignment: center-right;-fx-padding: 2; -fx-spacing: 4");
                time.setText(message.getSentAt().format(formatter) + " ✔");
                holder.getChildren().add(vBox);
                holder.getChildren().add(PFP);
            } else {
                vBox.setStyle("-fx-background-color: #4d8888; -fx-padding: 8; -fx-background-radius: 12");
                holder.setStyle("-fx-alignment: center-left;-fx-padding: 2; -fx-spacing: 4");
                holder.getChildren().add(PFP);
                holder.getChildren().add(vBox);
            }
            messagesList.getChildren().add(holder);
        }
    }

    @FXML
    private void sendChatMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            Communicator request = new Communicator("send");
            request.addData("type", "global");
            request.addData("text", messageTextField.getText());
            ConnectionController.send(request);
            ChatMessage message = new ChatMessage(UserController.getInstance().getLoggedInUser().getUsername(), messageTextField.getText());
            ChatDatabase.addGlobalChatMessage(message);
            messageTextField.setText("");
            showChatMessages();
        }
    }

    @FXML
    private void goToPrivateChat() {
        ConnectionController.endListenerThread = true;
        App.setMenu("private_chatroom");
    }

    @FXML
    private void goToGroupChat() {
        ConnectionController.endListenerThread = true;
        App.setMenu("group_chatroom");
    }

    @FXML
    private void backToMainMenu() {
        ConnectionController.endListenerThread = true;
        App.setMenu("main_menu");
    }

    public void showMessages() {
    }
}
