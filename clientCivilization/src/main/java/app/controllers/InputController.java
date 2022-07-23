package app.controllers;

import app.models.connection.Message;
import app.views.*;
import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class InputController extends Thread{

    private static InputController instance;
    private static HashMap<String, Object> data = new HashMap<>();
    private static ArrayList<Message> inputRequests = new ArrayList<>();

    private Message message;
    private String response;

    public static InputController getInstance() {
        if (instance == null) instance = new InputController();
        return instance;
    }
    public void run(){
            response = null;
            try {
                System.out.println("listen...");
                response = ConnectionController.listen();
                System.out.println("finish :)");
            } catch (IOException exception) {
                exception.printStackTrace();
                if (exception instanceof EOFException || exception instanceof SocketException);
                System.err.println("con not readUTF : ConnectionController listen()");
            }
            try {
                message = new Gson().fromJson(response, Message.class);
                HandlerMessage(message);
                data.putAll(message.getAllData());
            }catch (Exception exception){
                System.out.println("not Message format");
            }
    }

    private void HandlerMessage(Message message) {

        switch (message.getWhichMenu()){
            case "main":
                MainMenu.setAndPrintMessage(message);
                break;
            case "profile":
                ProfileMenu.setAndPrintMessage(message);
                break;
            case "register":
                RegisterMenu.setAndPrintMessage(message);
                break;
            case "game":
                GameMenu.setAndPrintMessage(message);
                break;
        }

        if (message.getData("input") != null) {
            String input = Menu.getInput((String) message.getData("input"));
            ConnectionController.sendString(input);
        }
        if (message.getAllData().containsKey("continue"))
            this.run();
    }


    public Object getObject(String name){
        System.out.println("InputCon getInteger : name : " + name);
        int k = 0;
        while (!data.containsKey(name)){
            k++;
        }
        System.out.println(k);
        Object ob = data.get(name);
        data.remove(name);
        return ob;

    }

    public Message getMessage() {
        InputController inputController = new InputController();
        inputController.run();
        return inputController.message;
    }
}
