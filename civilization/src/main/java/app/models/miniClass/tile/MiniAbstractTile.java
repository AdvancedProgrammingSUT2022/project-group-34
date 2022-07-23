package app.models.miniClass.tile;

import app.controllers.GameController;
import app.models.tile.AbstractTile;

import java.util.ArrayList;

public class MiniAbstractTile {

    protected int x;
    protected int y;
    protected int mC;
    protected String tu;
    protected String fe;
    protected Integer cy;//todo
    protected Integer ci;//todo
    protected ArrayList<Integer> adjacentTiles = new ArrayList<>();
    protected ArrayList<Boolean> iR = new ArrayList<>();
    protected boolean iB = false;

    public MiniAbstractTile(AbstractTile abstractTile) {
        this.x = abstractTile.getX();
        this.y = abstractTile.getY();
        this.mC = abstractTile.getMovingCost();
        try {
            this.tu = abstractTile.getTerrain().getName();
        } catch (Exception e){
            this.tu = null;
        }
        try {
            this.fe = abstractTile.getFeature().getName();
        } catch (Exception e){
            this.fe = null;
        }
        this.iR = abstractTile.getIsRiver();
        this.iB = abstractTile.isBlock();
        //abstractTile.getAdjacentTiles().forEach(adj -> this.adjacentTiles.add(GSave.getInstance().miniSave(adj)));
        try {
            this.cy = abstractTile.getCivilization().getCities().indexOf(abstractTile.getCity());
        }catch (Exception ignored){
            this.cy = null;
        }
        try {
            this.ci = GameController.getInstance().getGame().getCivilizations().indexOf(abstractTile.getCivilization());
        }catch (Exception ignored){
            ci = null;
        }
    }
}
