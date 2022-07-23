package app.models.miniClass.tile;

import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.VisibleTile;

public class MiniVisibleTile extends MiniAbstractTile {

    private boolean iIF;

    public MiniVisibleTile() {
        super();
    }

    public VisibleTile getOriginal() {
        VisibleTile visibleTile = new VisibleTile();
        visibleTile.setX(x);
        visibleTile.setY(y);
        visibleTile.setMovingCost(mC);
        visibleTile.setTerrain(Terrain.getAllTerrains().get(tu));
        visibleTile.setFeature(Feature.getAllFeatures().get(fe));
        //protected City city;
        //protected Civilization civilization;
        //protected ArrayList<Tile> adjacentTiles = new ArrayList<>();
        visibleTile.setIsRiver(this.iR);
        visibleTile.setBlock(false);
        visibleTile.setInFog(this.iIF);
        return visibleTile;
    }
}
