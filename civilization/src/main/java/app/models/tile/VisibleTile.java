package app.models.tile;

import app.models.unit.CombatUnit;
import app.models.unit.NonCombatUnit;

public class VisibleTile extends AbstractTile{
    boolean isInFog = true;

    public VisibleTile() {
        super();
    }

    public VisibleTile(AbstractTile tile, boolean isInFog) {
        super(tile.clone());
        this.isInFog = isInFog;
    }

    public void setInFog(boolean inFog) {
        isInFog = inFog;
    }

    public boolean isInFog() {
        return isInFog;
    }

    @Override
    public CombatUnit getCombatUnit() {
        return null;
    }

    @Override
    public NonCombatUnit getNonCombatUnit() {
        return null;
    }
}