package models.tile;

import models.City;

import java.util.ArrayList;

public abstract class AbstractTile {
    protected int x;
    protected int y;
    protected int movingCost;
    protected Terrain terrain;
    protected Feature feature;;
    protected City city;
    protected ArrayList<Tile> adjacentTiles = new ArrayList<>();
    protected ArrayList<Boolean> isRiver = new ArrayList<>();
    protected boolean isBlock = false;

    public AbstractTile(Terrain terrain, Feature feature, int x, int y, City city) {
        this.terrain = terrain;
        this.feature = feature;
        this.x = x;
        this.y = y;
        this.city = city;
        for (int i = 0; i < 6; i++) isRiver.add(false);
    }
}