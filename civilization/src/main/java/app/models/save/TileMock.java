package app.models.save;

import app.controllers.singletonController.GLoad;
import app.controllers.singletonController.GSave;
import app.models.City;
import app.models.Civilization;
import app.models.resource.Resource;
import app.models.resource.ResourceEnum;
import app.models.tile.Improvement;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;
import app.models.unit.CombatUnit;
import app.models.unit.NonCombatUnit;

public class TileMock extends AbstractTileMock{

    private ImprovementEnum improvement = null;
    private ResourceEnum resource = null;
    boolean isUsableResource = false;

    int foodRate;
    int goldRate;
    int productionRate;
    int impactOnWar;
    private boolean hasRoad;
    private boolean hasRail;
    private boolean isLooted;
    private Integer nonCombatUnitID;
    private Integer combatUnitID;

    public TileMock(Tile tile, Integer id) {
        super(tile, id);
        this.improvement = GSave.getInstance().save(tile.getImprovement());
        this.resource = GSave.getInstance().save(tile.getResource());
        this.isUsableResource = tile.isUsableResource();
        this.foodRate = tile.getFoodRate();
        this.goldRate = tile.getGoldRate();
        this.productionRate = tile.getProductionRate();
        this.impactOnWar = tile.getImpactOnWar();
        this.hasRoad = tile.hasRoad();
        this.hasRail = tile.hasRail();
        this.isLooted = tile.isLooted();
        this.nonCombatUnitID = GSave.getInstance().save(tile.getNonCombatUnit());
        this.combatUnitID = GSave.getInstance().save(tile.getNonCombatUnit());
    }

    public TileMock() {
        super(0);
    }


    @Override
    public Tile getOriginalObject() {
        Tile tile = new Tile(this.terrain, this.feature, this.x, this.y, (City) GLoad.getInstance().load(new CityMock(), this.cityID),
                (Civilization) GLoad.getInstance().load(new CivilizationMock(), this.civilizationID));

        tile.setImprovement(Improvement.allImprovements.get(this.improvement));
        tile.setResource(Resource.getAllResourcesCopy().get(this.resource));

        tile.setHasRail(this.hasRail);
        tile.setHasRoad(this.hasRoad);
        tile.setLooted(this.isLooted);
        tile.setCombatUnit((CombatUnit) GLoad.getInstance().load(new UnitMock(),this.combatUnitID));
        tile.setNonCombatUnit((NonCombatUnit) GLoad.getInstance().load(new UnitMock(),this.nonCombatUnitID));

        return tile;
    }
}
