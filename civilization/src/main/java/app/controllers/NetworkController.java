package app.controllers;

import app.models.connection.Processor;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkController {
    private static Socket socket;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;



    public static void connect() {
        try {
            socket = new Socket("localhost", 8000);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("connected");
        } catch (IOException e) {
            e.printStackTrace();
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
