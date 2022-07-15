package app.models.save;

import app.controllers.GSave;
import app.models.resource.ResourceEnum;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;

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
    private Integer NonCombatUnitID;
    private Integer combatUnit;

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
        this.NonCombatUnitID = GSave.getInstance().save(tile.getNonCombatUnit());
        this.combatUnit = GSave.getInstance().save(tile.getNonCombatUnit());
    }


    @Override
    public Object getOriginalObject() {
        return null;
    }
}
