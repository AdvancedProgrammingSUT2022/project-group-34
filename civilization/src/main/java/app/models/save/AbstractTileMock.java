package app.models.save;

import app.controllers.GSave;
import app.models.tile.AbstractTile;
import app.models.tile.Feature;
import app.models.tile.Terrain;

import java.util.ArrayList;

public abstract class AbstractTileMock extends Mock{

    protected int x;
    protected int y;
    protected int movingCost;
    protected Terrain terrain;
    protected Feature feature;;
    protected Integer cityID;
    protected Integer civilizationID;
    protected ArrayList<Boolean> isRiver = new ArrayList<>();
    protected boolean isBlock = false;

    public AbstractTileMock(AbstractTile abstractTile, Integer id){
        super(id);
        this.x = abstractTile.getX();
        this.y = abstractTile.getY();
        this.movingCost = abstractTile.getMovingCost();
        this.terrain = abstractTile.getTerrain();
        this.feature = abstractTile.getFeature();
        this.cityID = GSave.getInstance().save(abstractTile.getCity());
        this.civilizationID = GSave.getInstance().save(abstractTile.getCivilization());;
        this.isRiver = abstractTile.getIsRiver();
        this.isBlock = abstractTile.isBlock();

        //abstractTile.getAdjacentTiles();
    }

    public AbstractTileMock(int id) {
        super(id);
    }
}
