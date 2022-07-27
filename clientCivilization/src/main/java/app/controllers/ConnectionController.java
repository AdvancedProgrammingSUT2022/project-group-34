package app.controllers;

import app.models.ChatDatabase;
import app.models.connection.*;
import app.views.graphicalMenu.PublicChatroomController;
import com.google.gson.*;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConnectionController {

    private static final int serverID = 9000;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

    public static boolean endListenerThread = false;
    private static Object controller;


    public static boolean connection(){
        try {
            Socket socket = new Socket("localhost", serverID);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.err.println("connection is failed");
            return false;
        }
    }

    public static void send(Processor processor) {
        try {
            outputStream.writeUTF(new Gson().toJson(processor));
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("writeUTF() failed");
        }
    }

    public static void sendString(String string){
        try {
            outputStream.writeUTF(string);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("writeUTF() failed");
        }
    }

    public static String listen() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        while ((!(str = inputStream.readUTF()).equals(""))){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
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

    public static Communicator send(Communicator communicator) {
        try {
            outputStream.writeUTF(communicator.toJson());
            outputStream.flush();
            return Communicator.fromJson(inputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void handleUpdate(Communicator update) {
        System.out.println(update.getTitle());
        switch (update.getTitle()) {
            case "updateGlobal": {
                String messageJson = new Gson().toJson(update.getData().get("message"));
                System.out.println(2);
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                }).create();
                ChatMessage ChatMessage = gson.fromJson(messageJson, ChatMessage.class);
                ChatDatabase.addGlobalMessage(ChatMessage);

                Platform.runLater(() -> ((PublicChatroomController) controller).showMessages());
            }
        }
    }

    public static Object getController() {
        return controller;
    }

    public static void setController(Object controller) {
        ConnectionController.controller = controller;
    }

}


