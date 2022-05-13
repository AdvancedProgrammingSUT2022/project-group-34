package models.tile;

import models.City;
import models.Civilization;
import models.unit.CombatUnit;
import models.unit.NonCombatUnit;

import java.util.ArrayList;

public abstract class AbstractTile {
    protected int x;
    protected int y;
    protected int movingCost;
    protected Terrain terrain;
    protected Feature feature;;
    protected City city;
    protected Civilization civilization;
    protected ArrayList<Tile> adjacentTiles = new ArrayList<>();
    protected ArrayList<Boolean> isRiver = new ArrayList<>();
    protected boolean isBlock = false;

    public AbstractTile() {
    }

    public AbstractTile(AbstractTile tile) {
        this.x = tile.x;
        this.y = tile.y;
        this.movingCost = tile.movingCost;
        this.terrain = tile.terrain;
        this.feature = tile.feature;;
        this.city = tile.city;
        this.adjacentTiles = tile.adjacentTiles;
        this.isRiver = tile.isRiver;
        this.isBlock = tile.isBlock;
        this.civilization = tile.civilization;
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

    public abstract CombatUnit getCombatUnit();
    public abstract NonCombatUnit getNonCombatUnit();

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
        tile.feature = feature;;
        tile.city = city;
        tile.adjacentTiles = adjacentTiles;
        tile.isRiver = isRiver;
        tile.isBlock = isBlock;
        return tile;
    }

}