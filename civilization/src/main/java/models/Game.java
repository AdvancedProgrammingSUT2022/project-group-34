package models;

import models.map.GameMap;
import models.resource.Resource;
import models.unit.Unit;
import models.unit.Settler;
import models.tile.Tile;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private HashMap<String, Resource> gameResources;
    private HashMap<String, Technology> gameTechnologies;

    private ArrayList<Civilization> civilizations;
    private ArrayList<User> users;

    private GameMap mainGameMap;

    private int tern;

    public Game(ArrayList<User> users) {
        mainGameMap = GameMap.load();
        ArrayList<Settler> settlers = makeRandomSettlers(mainGameMap, users.size());
        for (int i = 0; i < users.size(); i++) {
            Settler settler = settlers.get(i);
            ArrayList<City> cityList = new ArrayList<>();
            ArrayList<Unit> unitList = new ArrayList<>();
            unitList.add(settler);
            Civilization civilization = new Civilization(users.get(i), users.get(i).getNickname(), cityList, unitList, null, 0, 0, 0, 0);
            civilizations.add(civilization);
            settler.setCivilization(civilization);
        }
        this.users = users;
        //personal maps initialized in controller.
    }

    public HashMap<String, Resource> getGameResources() {
        return gameResources;
    }

    public void setGameResources(HashMap<String, Resource> gameResources) {
        this.gameResources = gameResources;
    }

    public HashMap<String, Technology> getGameTechnologies() {
        return gameTechnologies;
    }

    public void setGameTechnologies(HashMap<String, Technology> gameTechnologies) {
        this.gameTechnologies = gameTechnologies;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public void setCivilizations(ArrayList<Civilization> civilizations) {
        this.civilizations = civilizations;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public GameMap getMainGameMap() {
        return mainGameMap;
    }

    public void setMainGameMap(GameMap mainGameMap) {
        this.mainGameMap = mainGameMap;
    }

    public int getTern() {
        return tern;
    }

    public void setTern(int tern) {
        this.tern = tern;
    }

    private ArrayList<Settler> makeRandomSettlers(GameMap mainGameMap, int count) {
        ArrayList<Tile> candidateTiles = new ArrayList<>();
        for (int row = 0; row < mainGameMap.getMapHeight(); row++) {
            for (int column = 0; column < mainGameMap.getMapWidth(); column++) {
                Tile tile = mainGameMap.getTileByXY(row, column);
                if (tile != null && !tile.isUnmovable()) candidateTiles.add(tile);
            }
        }
        Collections.shuffle(candidateTiles);
        ArrayList<Settler> answer = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            answer.add(new Settler("Initial settler", candidateTiles.get(i)));
        }
        return answer;
    }
}
