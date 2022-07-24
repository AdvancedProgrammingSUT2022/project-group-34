package app.models;

import app.controllers.InputController;
import app.models.map.GameMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private GameMap mainGameMap;


    public Game() {
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public GameMap getMainGameMap() {
        return mainGameMap;
    }

    public void setMainGameMap(GameMap mainGameMap) {
        this.mainGameMap = mainGameMap;
    }


    public ArrayList<User> getUsers() {
        String nameField = "cities";
        String json = getField(nameField);
        return new Gson().fromJson(json, new TypeToken<List<User>>(){}.getType());
    }

    private String getField(String nameField) {
        String category = "Civilization";
        return InputController.getInstance().getField(nameField, category);
    }
}
