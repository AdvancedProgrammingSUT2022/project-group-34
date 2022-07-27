package controllers;

import com.google.gson.*;
import models.ChatDatabase;
import models.Communicator;
import models.Message;
import models.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatController {
    public static void addGlobalMessage(String text, User sender) {
        Message message = new Message(sender.getUsername(), text);
        message.setSentAt(LocalDateTime.now());

        ChatDatabase.addGlobalMessage(message);

        Communicator update = new Communicator("updateGlobal");
        update.addData("message", message);

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(formatter.format(localDateTime));
            }
        }).create();

        for (NetworkController networkController : NetworkController.getNetworkControllers()) {
            if (networkController.getUser().equals(sender))
                continue;
            DataOutputStream dataOutputStream = networkController.getUpateDataOutputStream();
            try {
                dataOutputStream.writeUTF(gson.toJson(update));
                dataOutputStream.flush();
                System.out.println("update sent to: " + networkController.getUser().getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
