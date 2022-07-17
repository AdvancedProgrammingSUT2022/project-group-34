package app.models.save;

import app.controllers.GLoad;
import app.models.City;
import app.models.Civilization;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;

public class VisibleTileMock extends AbstractTileMock {

    boolean isInFog;

    public VisibleTileMock(VisibleTile visibleTile, Integer id) {
        super(visibleTile,id);
        isInFog = visibleTile.isInFog();
    }

    @Override
    public VisibleTile getOriginalObject() {
        Tile tile = new Tile(this.terrain, this.feature, this.x, this.y, (City) GLoad.gIn().load(new CityMock(), this.cityID),
                (Civilization) GLoad.gIn().load(new CivilizationMock(), this.civilizationID));

        return new VisibleTile(tile,this.isInFog);
    }
}
