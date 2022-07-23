package app.models.miniClass.tile;

import app.models.tile.VisibleTile;

public class MiniVisibleTile extends MiniAbstractTile {

    private boolean iIF;

    public MiniVisibleTile(VisibleTile tile) {
        super(tile);
        this.iIF = tile.isInFog();
    }

}
