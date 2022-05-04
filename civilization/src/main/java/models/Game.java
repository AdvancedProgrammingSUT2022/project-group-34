package models;

import models.map.GameMap;
import models.resource.Resource;

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
        this.users = users;
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
}
