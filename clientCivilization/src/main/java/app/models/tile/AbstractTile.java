package app.models.tile;

import app.models.unit.CombatUnit;
import app.models.unit.NonCombatUnit;
import app.models.City;
import app.models.Civilization;
import app.models.unit.Unit;

import java.util.ArrayList;

public abstract class AbstractTile {

    protected int x;
    protected int y;
    protected int movingCost;
    protected Terrain terrain;
    protected Feature feature;
    protected City city;
    protected Civilization civilization;
    protected ArrayList<Tile> adjacentTiles = new ArrayList<>();
    protected ArrayList<Boolean> isRiver = new ArrayList<>();
    protected boolean isBlock = false;

    public AbstractTile() {
    }

    public AbstractTile(Terrain terrain, Feature feature, int x, int y, City city, Civilization civilization) {
        this.terrain = terrain;
        this.feature = feature;
        this.x = x;
        this.y = y;
        this.city = city;
        this.civilization = civilization;
        for (int i = 0; i < 6; i++) isRiver.add(false);
    }

    public abstract Unit getCombatUnit();
    public abstract Unit getNonCombatUnit();

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Feature getFeature() {
        return this.feature;
    }

    public ArrayList<Boolean> getIsRiver() {
        return isRiver;
    }

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public City getCity() {
        return city;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    @Override
    public AbstractTile clone() {
        VisibleTile tile = new VisibleTile();
        tile.x = x;
        tile.y = y;
        tile.movingCost = movingCost;
        tile.terrain = terrain;
        tile.feature = feature;
        tile.city = city;
        tile.adjacentTiles = adjacentTiles;
        tile.isRiver = isRiver;
        tile.isBlock = isBlock;
        return tile;
    }

    public int getMovingCost() {
        return movingCost;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMovingCost(int movingCost) {
        this.movingCost = movingCost;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    public void setIsRiver(ArrayList<Boolean> isRiver) {
        this.isRiver = isRiver;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }
}