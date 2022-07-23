package app.models.miniClass.tile;

import app.models.miniClass.MiniUnit;
import app.models.tile.Tile;

public class MiniTile extends MiniAbstractTile {
    private String ip = null;
    private String rs = null;
    private boolean iUR = false;

    int F;
    int G;
    int P;
    int W;
    private int RRL            = 0;
    private MiniUnit
            NU = null;
    private MiniUnit
            CU = null;

    public MiniTile(Tile tile){
        super(tile);
        try {
            ip = tile.getImprovement().getName();
        } catch (Exception e){
            ip = null;}
        try {
            rs = tile.getResource().getName();
        } catch (Exception e){
            rs = null;}

        iUR = tile.isUsableResource();
        F = tile.getFoodRate();
        G = tile.getGoldRate();
        P = tile.getProductionRate();
        W = tile.getImpactOnWar();
        if (tile.hasRail()) RRL += 1;
        if (tile.hasRoad()) RRL += 2;
        if (tile.isLooted()) RRL += 4;
        try {
            NU = new MiniUnit(tile.getNonCombatUnit());
        }catch (Exception ignored){}
        try {
            CU = new MiniUnit(tile.getCombatUnit());
        }catch (Exception ignored){}

    }
}
