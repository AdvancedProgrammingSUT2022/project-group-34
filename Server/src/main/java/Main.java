import controllers.NetworkController;
import controllers.UserController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        initializeNetwork();
    }

    private static void initializeNetwork() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                NetworkController networkController = new NetworkController(socket);
                networkController.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
