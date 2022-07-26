package app.models;

import app.controllers.InputController;
import app.models.connection.Processor;
import app.models.map.CivilizationMap;
import app.models.tile.Tile;
import app.views.commandLineMenu.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Civilization {


    private final String civilizationName;
    private final CivilizationMap personalMap;

    private HashMap<TechnologyEnum, Technology> civilizationResearchedTechnologies = new HashMap<>();
    private int turn = 0;


    public Civilization(String civilizationName, CivilizationMap personalMap, HashMap<TechnologyEnum, Technology> civilizationResearchedTechnologies, int turn) {
        this.civilizationName = civilizationName;
        this.personalMap = personalMap;
        this.civilizationResearchedTechnologies = civilizationResearchedTechnologies;
        this.turn = turn;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public CivilizationMap getPersonalMap() {
        return personalMap;
    }

    public HashMap<TechnologyEnum, Technology> getCivilizationResearchedTechnologies() {
        return civilizationResearchedTechnologies;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isInFog(Tile tile) {
        if (tile == null) return true;
        return personalMap.getTileByXY(tile.getX(), tile.getY()).isInFog();
    }

    public boolean isTransparent(Tile tile) {
        return personalMap.isTransparent(personalMap.getTileByXY(tile.getX(), tile.getY()));
    }

    public boolean hasResearched(TechnologyEnum technologyEnum) {
        return civilizationResearchedTechnologies.containsKey(technologyEnum);
    }

    public void setCivilizationResearchedTechnologies(HashMap<TechnologyEnum, Technology> civilizationResearchedTechnologies) {
        this.civilizationResearchedTechnologies = civilizationResearchedTechnologies;
    }

    public ArrayList<City> getCities(){
        String nameField = "cities";
        String json = getField(nameField);
        return new Gson().fromJson(json, new TypeToken<List<City>>(){}.getType());
    }

    public int getGold() {
        String nameField = "gold";
        String json = getField(nameField);
        return new Gson().fromJson(json, Integer.class);
    }

    public int getHappiness() {
        String nameField = "happiness";
        String json = getField(nameField);
        return new Gson().fromJson(json, Integer.class);
    }

    public int getNumberOfBeakers() {
        String nameField = "numberOfBeakers";
        String json = getField(nameField);
        return new Gson().fromJson(json, Integer.class);
    }

    public Technology getStudyingTechnology() {
        String nameField = "studyingTechnology";
        String json = getField(nameField);
        return new Gson().fromJson(json, Technology.class);
    }

    private String getField(String nameField) {
        String category = "Civilization";
        return (String) InputController.getInstance().getField(nameField, category);
    }


}
