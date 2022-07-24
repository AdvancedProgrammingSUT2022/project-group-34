package app.controllers;

import app.models.connection.Processor;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionController {

    private static final int serverID = 8000;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

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
}


