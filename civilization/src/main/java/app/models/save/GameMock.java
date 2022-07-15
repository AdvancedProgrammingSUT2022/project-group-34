package app.models.save;

import app.controllers.GSave;
import app.models.Game;
import app.models.resource.ResourceEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMock {

    private Integer id;
    private HashMap<String, ResourceEnum> gameResourcesID = new HashMap<>();
    private HashMap<String, Integer> gameTechnologiesID = new HashMap<>();
    private ArrayList<Integer> civilizationsID = new ArrayList<>();
    private ArrayList<Integer> usersID = new ArrayList<>();
    private Integer mainGameMapID = 0;
    private int tern;


    public GameMock(Game game,Integer id){
        this.id = id;
        game.getGameResources().forEach((s, resource) -> this.gameResourcesID.put(s,GSave.getInstance().save(resource)));
        game.getGameTechnologies().forEach((s, technology) -> this.gameTechnologiesID.put(s, GSave.getInstance().save(technology)));
        game.getCivilizations().forEach(civilization -> this.civilizationsID.add(GSave.getInstance().save(civilization)));
        game.getUsers().forEach(user -> this.usersID.add(GSave.getInstance().save(user)));
        mainGameMapID = GSave.getInstance().save(game.getMainGameMap());
        this.tern = game.getTern();
    }
}
