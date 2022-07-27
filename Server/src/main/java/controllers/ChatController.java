package controllers;

import com.google.gson.*;
import models.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChatController {
    public static void addGlobalMessage(String text, User sender) {
        Message message = new Message(sender.getUsername(), text);
        message.setId(ChatDatabase.getGlobalMessages().size());

        ChatDatabase.addGlobalMessage(message);

        Communicator update = new Communicator("updateGlobal");
        update.addData("message", message);

//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            @Override
//            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
//                return new JsonPrimitive(formatter.format(localDateTime));
//            }
//        }).create();

        for (NetworkController networkController : NetworkController.getNetworkControllers()) {
            if (networkController.getUser().equals(sender))
                continue;
            DataOutputStream dataOutputStream = networkController.getUpateDataOutputStream();
            try {
                dataOutputStream.writeUTF(update.toJson());
                dataOutputStream.flush();
                System.out.println("update sent to: " + networkController.getUser().getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addPrivateMessage(String text, User sender, User receiver) {
        Chat chat = getChat(sender, receiver);
        Message message = new Message(sender.getUsername(), text);
        message.setId(chat.getMessages().size());
        chat.addMessage(message);

        Communicator update = new Communicator("updateChatMessages");
        update.addData("message", message);
        update.addData("user", sender);

//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            @Override
//            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
//                return new JsonPrimitive(formatter.format(localDateTime));
//            }
//        }).create();

        System.out.println("sender"+sender.getUsername());
        System.out.println("receiver"+receiver.getUsername());

        DataOutputStream dataOutputStream = getDataOutputStream(receiver);
        if (dataOutputStream == null) {
            System.out.println("no");
            return;
        }
        try {
            dataOutputStream.writeUTF(update.toJson());
            dataOutputStream.flush();
            System.out.println("update for:"+receiver.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Chat addChat(User creator, User user) {
        Communicator update = new Communicator("updateChatChats");
        Chat chat = new Chat(creator.getUsername(), user.getUsername());
        chat.setId(ChatDatabase.getPrivateChats().size());
        ChatDatabase.addPrivateChat(chat);
        update.addData("chat", chat);

        DataOutputStream dataOutputStream = getDataOutputStream(user);
        if (dataOutputStream == null)
            return chat;
        try {
            dataOutputStream.writeUTF(update.toJson());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chat;
    }

    public static ArrayList<Chat> getChatsOfUser(User user) {
        String username = user.getUsername();
        ArrayList<Chat> chats = new ArrayList<>();
        for (Chat privateChat : ChatDatabase.getPrivateChats())
            if (privateChat.getUsername1().equals(username) || privateChat.getUsername2().equals(username))
                chats.add(privateChat);
        return chats;
    }

    public static Chat getChat(User user1, User user2) {
        ArrayList<Chat> chats1 = getChatsOfUser(user1);
        ArrayList<Chat> chats2 = getChatsOfUser(user2);
        return chats1.stream().filter(chats2::contains).collect(Collectors.toList()).get(0);
    }

    private static DataOutputStream getDataOutputStream(User user) {
        for (NetworkController networkController : NetworkController.getNetworkControllers())
            if (networkController.getUser().getUsername().equals(user.getUsername()))
                return networkController.getUpateDataOutputStream();
        return null;
    }
}
