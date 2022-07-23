package app.models.miniClass.tile;

import app.models.miniClass.Mini;
import app.models.tile.AbstractTile;

import java.util.ArrayList;

public class MiniAbstractTile extends Mini {

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

    public MiniAbstractTile() {

    }

    @Override
    public AbstractTile getOriginal(){
        return null;
    }
}
