package controllers;

import com.google.gson.*;
import models.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NetworkController extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private User user;

    private Socket updateSocket;
    private DataOutputStream upateDataOutputStream;

    private static ArrayList<NetworkController> networkControllers = new ArrayList<>();

    public NetworkController(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("new connection");
        try {
            while (true) {
                Communicator request;
                try {
                    request = Communicator.fromJson(dataInputStream.readUTF());
                } catch (IOException e) {
                    networkControllers.remove(this);
                    break;
                }
                System.out.println(socket);

                Communicator response = handleRequest(request);
                dataOutputStream.writeUTF(response.toJson());
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Communicator handleRequest(Communicator request) {
        Communicator communicator = new Communicator("ok");
        switch (request.getTitle()) {
            case "login":
                this.user = new Gson().fromJson(new Gson().toJson(request.getData().get("user")), User.class);
                networkControllers.add(this);
                return communicator;
            case "registerReader": {
                User user = new Gson().fromJson(new Gson().toJson(request.getData().get("user")), User.class);
                System.out.println(user);
                for (NetworkController networkController : networkControllers) {
                    if (networkController.getUser().getUsername().equals(user.getUsername())) {
                        networkController.setUpdateSocket(this.socket);
                        break;
                    }
                }
                System.out.println("Registered update socket for " + user.getUsername());
                return communicator;
            }
            case "getGlobalMessages":
                communicator.addData("messages", ChatDatabase.getGlobalMessages());
                System.out.println(1);
                return communicator;
            case "getChats":
                communicator.addData("chats", ChatController.getChatsOfUser(user));
                return communicator;
            case "addChat": {
                User user = new Gson().fromJson(new Gson().toJson(request.getData().get("user")), User.class);
                Chat chat = ChatController.addChat(this.user, user);
                communicator.addData("chat", chat);
                return communicator;
            }
            case "send":
                if (request.getData().get("type").equals("global")) {
                    ChatController.addGlobalMessage((String) request.getData().get("text"), user);
                    return communicator;
                } else if (request.getData().get("type").equals("private")) {
                    User user = new Gson().fromJson(new Gson().toJson(request.getData().get("user")), User.class);
                    ChatController.addPrivateMessage((String) request.getData().get("text"), this.user, user);
                }
                break;
            case "delete":
                if (request.getData().get("type").equals("global"))
                    ChatController.deleteGlobalMessage(request, user);
                else if (request.getData().get("type").equals("private")) {
                    System.out.println(request.getTitle());
                    System.out.println(request.getData().get("type"));
                    ChatController.deleteChatMessage(request, user);
                }
                break;
        }
        return new Communicator("error");
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public static ArrayList<NetworkController> getNetworkControllers() {
        return networkControllers;
    }

    public void setUpdateSocket(Socket updateSocket) {
        try {
            this.updateSocket = updateSocket;
            this.upateDataOutputStream = new DataOutputStream(updateSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getUpateDataOutputStream() {
        return upateDataOutputStream;
    }
}
