package app.models.save;

import app.models.tile.VisibleTile;

public class VisibleTileMock extends AbstractTileMock {

    boolean isInFog = true;

    public VisibleTileMock(VisibleTile visibleTile, Integer id) {
        super(visibleTile,id);
        isInFog = visibleTile.isInFog();
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
