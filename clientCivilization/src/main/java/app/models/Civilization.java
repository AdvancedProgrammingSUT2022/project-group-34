package app.models;

import app.models.map.CivilizationMap;
import app.models.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {


    private String civilizationName;

    private CivilizationMap personalMap;

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

    public boolean hasResearched(TechnologyEnum technology) {
        return civilizationResearchedTechnologies.containsKey(technology);
    }

    public ArrayList<City> getCities(){
        return null;
    }

    public void setCivilizationResearchedTechnologies(HashMap<TechnologyEnum, Technology> civilizationResearchedTechnologies) {
        this.civilizationResearchedTechnologies = civilizationResearchedTechnologies;
    }
}
