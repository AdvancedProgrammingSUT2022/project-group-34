package app.controllers.singletonController;

import app.models.connection.StringSocketToken;
import app.views.MySocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class NetworkController {

    private static final int serverPort = 10000;
    private final HashMap<StringSocketToken, MySocketHandler> mySocketHandlerHashMap = new HashMap<>();
    public static NetworkController networkController;
    private StringSocketToken stringSocketToken;

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
                    stringSocketToken = new StringSocketToken(UUID.randomUUID().toString());
                    MySocketHandler socketHandler = new MySocketHandler(socket, stringSocketToken);
                    mySocketHandlerHashMap.put(socketHandler.getSocketToken(),socketHandler);
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
