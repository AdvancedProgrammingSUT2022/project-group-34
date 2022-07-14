package app.models;

import app.models.map.GameMap;
import app.models.resource.ResourceEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMock {
    private HashMap<String, ResourceEnum> gameResources = new HashMap<>();
    private HashMap<String, TechnologyEnum> gameTechnologies = new HashMap<>();
    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<User> users;
    private GameMap mainGameMap;
    private int tern;


    public static GameMock GenerateGameMock(Game game){
        GameMock gameMock = new GameMock();
        game.getGameResources().forEach((s, resource) -> gameMock.gameResources.put(s,ResourceEnum.getResourceEnumByResource(resource)));
        game.getGameTechnologies().forEach(((s, technology) -> gameMock.gameTechnologies.put(s,TechnologyEnum.getTechnologyEnumByTechnology(technology))));
        return gameMock;
    }
}
