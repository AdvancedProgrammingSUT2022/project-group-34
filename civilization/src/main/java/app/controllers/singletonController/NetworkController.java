package app.controllers;

import app.serverView.MySocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class NetworkController {

    private static final int serverPort = 8000;
    private final HashMap<String, MySocketHandler> mySocketHandlerHashMap = new HashMap<>();
    public static NetworkController networkController;

    private NetworkController() {

    }

    public static NetworkController getInstance() {
        if (networkController == null)
            networkController = new NetworkController();
        return networkController;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("hello");
                    MySocketHandler socketHandler = new MySocketHandler(socket, UUID.randomUUID().toString());
                    mySocketHandlerHashMap.put(socketHandler.getToken(),socketHandler);
                    socketHandler.start();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
