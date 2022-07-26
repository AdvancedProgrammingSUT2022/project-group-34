package app.controllers;

import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.commandLineMenu.*;
import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

public class InputController extends Thread{

    private static InputController instance;

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
            }catch (Exception exception){
                System.out.println("not Message format");
            }
    }

    private void HandlerMessage(Message message) {

        if (message.getData("input") != null) {
            String input = Menu.getInput((String) message.getData("input"));
            ConnectionController.sendString(input);
        }
        if (message.getAllData().containsKey("continue"))
            this.run();
    }


    public Message getMessage() {
        Message message = null;
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
        }catch (Exception exception){
            System.out.println(response);
            System.out.println("not Message format");
        }

        if (!message.getWhichMenu().equals("isGetOrSet"))
            Menu.setMessage(message);
        return message;
    }

    public Object getField(String nameField, String category) {
        Processor processor = new Processor(category,"get", nameField);
        Menu.sendProcessor(processor);
        return InputController.getInstance().getMessage().getData(nameField);
    }
}
