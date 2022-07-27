package app.views;

import app.App;
import app.controllers.NetworkController;
import app.controllers.UserController;
import app.models.ChatDatabase;
import app.models.Communicator;
import app.models.Message;
import com.google.gson.*;
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

import java.lang.reflect.Type;
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
        NetworkController.setController(this);
        NetworkController.endListenerThread = false;
        NetworkController.listenForUpdates();
        globalChatButton.setDisable(true);
        getMessages();
        showMessages();
    }

    private void getMessages() {
        Communicator request = new Communicator("getGlobalMessages");
        Communicator response = NetworkController.send(request);
        String messageJson = new Gson().toJson(response.getData().get("messages"));
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        }).create();
        ArrayList<Message> messages = gson.fromJson(messageJson, new TypeToken<ArrayList<Message>>() {
        }.getType());
        ChatDatabase.setGlobalMessages(messages);
    }

    public void showMessages() {
        messagesList.getChildren().clear();
        messagesList.setStyle("-fx-pref-width: 910");
        ArrayList<Message> messages = ChatDatabase.getGlobalMessages();
        for (Message message : messages) {
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
    private void sendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            Communicator request = new Communicator("send");
            request.addData("type", "global");
            request.addData("text", messageTextField.getText());
            NetworkController.send(request);
            Message message = new Message(UserController.getInstance().getLoggedInUser().getUsername(), messageTextField.getText());
            ChatDatabase.addGlobalMessage(message);
            messageTextField.setText("");
            showMessages();
        }
    }

    @FXML
    private void goToPrivateChat() {
        NetworkController.endListenerThread = true;
        App.setMenu("private_chatroom");
    }

    @FXML
    private void goToGroupChat() {
        NetworkController.endListenerThread = true;
        App.setMenu("group_chatroom");
    }

    @FXML
    private void backToMainMenu() {
        NetworkController.endListenerThread = true;
        App.setMenu("main_menu");
    }

}
