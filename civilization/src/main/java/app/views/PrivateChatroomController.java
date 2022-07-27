package app.views;

import app.App;
import app.controllers.NetworkController;
import app.controllers.UserController;
import app.models.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PrivateChatroomController {
    @FXML
    private VBox chatsBox;
    @FXML
    private VBox usersBox;
    @FXML
    private TextField searchUserTextField;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button privateChatButton;

    private User friend;

    @FXML
    private void initialize() {
        NetworkController.setController(this);
        privateChatButton.setDisable(true);
        messageTextField.setDisable(true);
        getChats();
        showChats();
    }

    @FXML
    private void sendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            Chat chat = ChatDatabase.getChatByUser(friend);
            Communicator request = new Communicator("send");
            request.addData("type", "private");
            request.addData("user", friend);
            request.addData("text", messageTextField.getText());
            NetworkController.send(request);
            Message message = new Message(UserController.getInstance().getLoggedInUser().getUsername(), messageTextField.getText());
            message.setId(chat.getMessages().size());
            chat.addMessage(message);
            messageTextField.setText("");
            showMessages(friend);
        }
    }

    public void showMessages(User user) {
        chatsBox.getChildren().clear();
        chatsBox.setStyle("-fx-pref-width: 650");
        Chat chat;
        if ((chat = ChatDatabase.getChatByUser(user)) != null) {
            for (Message message : chat.getMessages()) {
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
                chatsBox.getChildren().add(holder);
            }
        }
    }


    @FXML
    private void searchForUser() {
        if (!searchUserTextField.getText().isEmpty()) {
            ArrayList<User> users = new ArrayList<>();
            for (User user : UserController.getInstance().getUsers())
                if (user.getUsername().startsWith(searchUserTextField.getText())
                        && !user.equals(UserController.getInstance().getLoggedInUser()))
                    users.add(user);

            showUsers(users);
        }
    }

    private void showUsers(ArrayList<User> users) {
        usersBox.getChildren().clear();
        usersBox.setStyle("-fx-pref-width: 270; -fx-pref-height: 10; -fx-spacing: 0");
        if (users.size() == 0)
            usersBox.getChildren().add(new Text("No result found!"));
        else {
            for (User user : users) {
                HBox holder = new HBox();
                holder.getChildren().add(new Text(user.getUsername()));
                if (!user.equals(friend))
                    holder.setStyle("-fx-background-color: #ecb8b8;-fx-border-color: #aa5555; -fx-border-width: 0 2 2 2; -fx-alignment: center-left;-fx-padding: 8;");
                else
                    holder.setStyle("-fx-background-color: #a1e1e1; -fx-border-color: #aa5555;-fx-border-width: 0 2 2 2; -fx-alignment: center-left;-fx-padding: 8");

                holder.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        friend = user;
                        Chat chat;
                        if ((chat=ChatDatabase.getChatByUser(friend)) == null) {
                            Communicator request = new Communicator("addChat");
                            request.addData("user", friend);
                            Communicator response = NetworkController.send(request);
                            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                                @Override
                                public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                                    return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                }
                            }).create();
                            String jsonString = new Gson().toJson(response.getData().get("chat"));
                            Chat newChat = gson.fromJson(jsonString, Chat.class);
                            ChatDatabase.addPrivateChat(newChat);
                        }else
                            showMessages(friend);

                        messageTextField.setDisable(false);
                        showChats();
                    }
                });
                usersBox.getChildren().add(holder);
            }
        }
    }

    @FXML
    public void showChats() {
        searchUserTextField.setText("");
        ArrayList<User> users = convertChatsToUsers();
        showUsers(users);
    }

    private ArrayList<User> convertChatsToUsers() {
        User user = UserController.getInstance().getLoggedInUser();
        ArrayList<User> users = new ArrayList<>();
        System.out.println(ChatDatabase.getGlobalMessages());
        System.out.println(ChatDatabase.getPrivateChats());

        for (Chat chat : ChatDatabase.getPrivateChats()) {
            if (chat.getUsername1().equals(user.getUsername()))
                users.add(UserController.getInstance().getUserByUsername(chat.getUsername2()));
            else users.add(UserController.getInstance().getUserByUsername(chat.getUsername1()));
        }
        return users;
    }


    private void getChats() {
        Communicator request = new Communicator("getChats");
        Communicator response = NetworkController.send(request);
        String jsonString = new Gson().toJson(response.getData().get("chats"));
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        }).create();
        ArrayList<Chat> chats = gson.fromJson(jsonString, new TypeToken<ArrayList<Chat>>() {
        }.getType());

        ChatDatabase.setPrivateChats(chats);
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
        NetworkController.endListenerThread = true;
        App.setMenu("main_menu");
    }
}
