package app.serverView;

import app.models.connection.StringGameToken;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.models.connection.StringSocketToken;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class MySocketHandler extends Thread{

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private final ServerGameMenu serverGameMenu;
    private final ServerProfileMenu serverProfileMenu;
    private final ServerRegisterMenu serverRegisterMenu;
    private final ServerMainMenu serverMainMenu;
    private Processor processor;
    private boolean threadFlag = true;
    private final StringSocketToken socketToken;
    protected StringGameToken gameToken = null;
    private boolean hasOpenProgress = false;

    public MySocketHandler(Socket socket, StringSocketToken socketToken){

        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.socketToken = socketToken;

        serverGameMenu = new ServerGameMenu(this);
        serverProfileMenu = new ServerProfileMenu(this);
        serverRegisterMenu = new ServerRegisterMenu(this);
        serverMainMenu = new ServerMainMenu(this);
    }


    @Override
    public void run() {
        while (threadFlag){
            processor = new Gson().fromJson(listen(),Processor.class);
            if (processor == null){
                System.out.println("processor is null");
                continue;
            }
            System.out.println(processor);
            switch (processor.getWhichMenu()){
                case "main":
                    serverMainMenu.processOneProcessor(processor);
                    break;
                case "register":
                    serverRegisterMenu.processOneProcessor(processor);
                    break;
                case "game":
                    serverGameMenu.processOneProcessor(processor);
                    break;
                case "profile":
                    serverProfileMenu.processOneProcessor(processor);
                    break;

            }
            if (hasOpenProgress)
                sendFinishProgress();
        }
    }

    private void sendFinishProgress() {
        Message message = new Message();
        send(message);
    }

    void sendMessage(Message message) {
        message.setWhichMenu(processor.getWhichMenu());
        System.out.println(message);

        hasOpenProgress = message.getData("continue") != null;

        System.out.println("send message...");
        send(new Gson().toJson(message));
        System.out.println("finish :)");
    }

    String listen() {
        try {
            return inputStream.readUTF();
        } catch (IOException e) {
            if (e instanceof EOFException || e instanceof SocketException)
                threadFlag = false;
        }
        return null;
    }

    boolean send(Object s){
        String str = s.toString();
        try {
            for (int i = 0; i < str.length(); i += 10000) {
                if (i + 10000 > str.length()) {
                    outputStream.writeUTF(str.substring(i));
                }
                else outputStream.writeUTF(str.substring(i,i+10000));
            }
            outputStream.writeUTF("");
            outputStream.flush();
            return true;
        } catch (IOException e) {
            if (e instanceof EOFException || e instanceof SocketException)
                threadFlag = false;
        }
        return false;
    }

    public StringSocketToken getSocketToken() {
        return socketToken;
    }

    public StringGameToken getGameToken() {
        return gameToken;
    }

    public void setGameToken(StringGameToken gameToken) {
        this.gameToken = gameToken;
    }


}
