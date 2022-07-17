package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
import app.models.Civilization;
import app.models.Game;
import app.models.Technology;
import app.models.User;
import app.models.map.GameMap;
import app.models.resource.Resource;
import app.models.resource.ResourceEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMock extends Mock{

    private HashMap<String, ResourceEnum> gameResourcesID = new HashMap<>();
    private HashMap<String, Integer> gameTechnologiesID = new HashMap<>();
    private ArrayList<Integer> civilizationsID = new ArrayList<>();
    private ArrayList<Integer> usersID = new ArrayList<>();
    private Integer mainGameMapID = 0;
    private int tern;

    public GameMock(){
        super(0);
    }

    public GameMock(Game game,Integer id){
        super(id);
        game.getGameResources().forEach((s, resource) -> this.gameResourcesID.put(s,GSave.getInstance().save(resource)));
        game.getGameTechnologies().forEach((s, technology) -> this.gameTechnologiesID.put(s, GSave.getInstance().save(technology)));
        game.getCivilizations().forEach(civilization -> this.civilizationsID.add(GSave.getInstance().save(civilization)));
        game.getUsers().forEach(user -> this.usersID.add(GSave.getInstance().save(user)));
        mainGameMapID = GSave.getInstance().save(game.getMainGameMap());
        this.tern = game.getTern();
    }

    @Override
    public Game getOriginalObject() {

        Game game = Game.getOneInstance();


        HashMap<String, Resource> gameResources = new HashMap<>();
        this.gameResourcesID.forEach((string, resourceEnum) -> gameResources.put(string, ResourceEnum.getResourceByEnum(resourceEnum)));
        game.setGameResources(gameResources);

        HashMap<String, Technology> gameTechnologies = new HashMap<>();
        this.gameTechnologiesID.forEach((string , integer) -> gameTechnologies.put(string, (Technology) GLoad.gIn().load(new TechnologyMock(),integer)));
        game.setGameTechnologies(gameTechnologies);

        ArrayList<Civilization> civilizations = new ArrayList<>();
        this.civilizationsID.forEach(id -> civilizations.add((Civilization) GLoad.gIn().load(new CivilizationMock(),id)));
        game.setCivilizations(civilizations);

        ArrayList<User> users = new ArrayList<>();
        this.usersID.forEach(id -> users.add((User) GLoad.gIn().load(new UserMock(),id)));
        game.setUsers(users);

        game.setMainGameMap((GameMap) GLoad.gIn().load(new GameMapMock(),this.mainGameMapID));
        game.setTern(this.tern);



        return game;
    }
}
