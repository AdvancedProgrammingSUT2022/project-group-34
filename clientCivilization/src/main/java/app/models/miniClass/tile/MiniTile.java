package app.models.miniClass.tile;

import app.models.miniClass.MiniUnit;
import app.models.resource.Resource;
import app.models.tile.*;
import app.models.unit.Unit;

public class MiniTile extends MiniAbstractTile {
    private String ip = null;
    private String rs = null;
    private boolean iUR = false;

    int F;
    int G;
    int P;
    int W;
    private int RRL= 0;
    private MiniUnit
            NU = null;
    private MiniUnit
            CU = null;

    @Override
    public AbstractTile getOriginal(){
        Tile tile = new Tile();
        tile.setX(x);
        tile.setY(y);
        tile.setMovingCost(mC);
        tile.setTerrain(Terrain.getAllTerrains().get(tu));
        tile.setFeature(Feature.getAllFeatures().get(fe));
        //protected City city;
        //protected Civilization civilization;
        //protected ArrayList<Tile> adjacentTiles = new ArrayList<>();
        tile.setIsRiver(this.iR);
        tile.setBlock(false);

        tile.setImprovement(ImprovementEnum.getImprovementByName(this.ip));
        tile.setResource(Resource.getAllResourcesCopyString().get(this.rs));
        tile.setUsableResource(iUR);
        tile.setFoodRate(F);
        tile.setGoldRate(G);
        tile.setProductionRate(P);
        tile.setImpactOnWar(W);
        tile.setHasRail(RRL%2 == 1);
        tile.setHasRoad((RRL/2)%2 == 1);
        tile.setLooted((RRL/4)%2 == 1);
        try {
            tile.setNonCombatUnit(new Unit(NU.getName(), NU.getIndex()));
        } catch (Exception e){
            tile.setNonCombatUnit(null);
        }
        try {
            tile.setCombatUnit(new Unit(CU.getName(), CU.getIndex()));
        } catch (Exception e){
            tile.setCombatUnit(null);
        }

        return tile;
    }

}
