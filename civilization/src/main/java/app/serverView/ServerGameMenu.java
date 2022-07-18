package app.serverView;

public class ServerGameMenu extends ServerMenu{

    private static ServerGameMenu instance;

    private ServerGameMenu(){

    }

    public static ServerGameMenu getInstance() {
        if (instance == null) instance = new ServerGameMenu();
        return instance;
    }
}
