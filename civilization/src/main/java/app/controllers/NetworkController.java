package app.controllers;

import app.models.*;
import app.views.PrivateChatroomController;
import app.views.PublicChatroomController;
import com.google.gson.*;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NetworkController {
    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static boolean endListenerThread = false;

    private static Object controller;

    public static void connect() {
        try {
            socket = new Socket("localhost", 8000);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Communicator send(Communicator communicator) {
        try {
            dataOutputStream.writeUTF(communicator.toJson());
            dataOutputStream.flush();
            return Communicator.fromJson(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void listenForUpdates() {
        Socket readerSocket;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream;
        try {
            readerSocket = new Socket("localhost", 8000);
            dataInputStream = new DataInputStream(readerSocket.getInputStream());
            dataOutputStream = new DataOutputStream(readerSocket.getOutputStream());
            Communicator communicator = new Communicator("registerReader");
            communicator.addData("user", UserController.getInstance().getLoggedInUser());
            dataOutputStream.writeUTF(communicator.toJson());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream finalDataInputStream = dataInputStream;
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (endListenerThread)
                        break;
                    System.out.println("Waiting for update");
                    Communicator update = Communicator.fromJson(finalDataInputStream.readUTF());
                    System.out.println("update received");
                    handleUpdate(update);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static void handleUpdate(Communicator update) {
        System.out.println(update.getTitle());
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        }).create();
        switch (update.getTitle()) {
            case "updateGlobal": {
                String messageJson = new Gson().toJson(update.getData().get("message"));
                System.out.println(2);
                Message message = gson.fromJson(messageJson, Message.class);
                ChatDatabase.addGlobalMessage(message);

                if (controller instanceof PublicChatroomController)
                    Platform.runLater(() -> ((PublicChatroomController) controller).showMessages());
            }
            case "updateChatChats": {
                Chat chat = gson.fromJson(new Gson().toJson(update.getData().get("chat")), Chat.class);
                ChatDatabase.addPrivateChat(chat);

                if (controller instanceof PrivateChatroomController)
                    Platform.runLater(() -> ((PrivateChatroomController) controller).showChats());
            }
            case "updateChatMessages": {
                System.out.println(new Gson().toJson(update.getData().get("user")));
                User user = new Gson().fromJson(new Gson().toJson(update.getData().get("user")), User.class);
                System.out.println(user);
                Chat chat = ChatDatabase.getChatByUser(user);
                Message message = gson.fromJson(new Gson().toJson(update.getData().get("message")), Message.class);
                chat.addMessage(message);

                if (controller instanceof PrivateChatroomController)
                    Platform.runLater(() -> ((PrivateChatroomController) controller).showMessages(user));
            }
            case "delete": {
                if (update.getData().get("type").equals("global")) {
                    int id = (int) ((double) update.getData().get("id"));
                    ChatDatabase.getGlobalMessages().remove(ChatDatabase.getMessageById(id));

                    if (controller instanceof PublicChatroomController)
                        Platform.runLater(() -> ((PublicChatroomController) controller).showMessages());
                }else if (update.getData().get("type").equals("private")){
                    int chatId = (int) (double)update.getData().get("chatId");
                    int messageId = (int) (double)update.getData().get("messageId");

                    Chat chat = ChatDatabase.getChatById(chatId);
                    chat.getMessages().remove(ChatDatabase.getMessageOfAChat(chat,messageId));

                    User sender;
                    if (chat.getUsername1().equals(UserController.getInstance().getLoggedInUser().getUsername()))
                        sender = UserController.getInstance().getUserByUsername(chat.getUsername2());
                    else
                        sender = UserController.getInstance().getUserByUsername(chat.getUsername1());

                    if (controller instanceof PrivateChatroomController)
                        Platform.runLater(() -> ((PrivateChatroomController) controller).showMessages(sender));
                }
            }
        }
    }

    public static Object getController() {
        return controller;
    }

    public static void setController(Object controller) {
        NetworkController.controller = controller;
    }
}
