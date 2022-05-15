package models.unit;

import models.City;
<<<<<<< HEAD
import models.Civilization;
=======
>>>>>>> bab60cdcb7713cdd55cd0e36c931fd758a1ddc3d
import models.TechnologyEnum;
import models.resource.ResourceEnum;
import models.tile.Tile;

import java.util.HashMap;

public class CombatUnit extends Unit{

    public static HashMap<String, HashMap<String, String>> combatUnitDataSheet = new HashMap<>();
    public static int hitPointConstant;

    protected String combatType;
    protected int range;
    protected int combatStrength;
    protected int hitPoint;

    protected boolean isSleep;
    protected boolean isAlert;
    protected boolean isFortify;
    protected boolean isFortifyUntilHealed;
    protected City garrisonCity;
    protected boolean isVisible;
    protected TechnologyEnum requiredTechnology;
    protected ResourceEnum requiredResource;
    private City GarrisonCity;


    public CombatUnit(UnitEnum unitEnum, Tile position, Civilization civilization) {
        super(unitEnum, position, civilization);

        this.requiredTechnology = unitEnum.requiredTechnology;
        this.requiredResource = unitEnum.requiredResource;
        this.combatStrength = unitEnum.combatStrength;
        this.combatType = unitEnum.combatType;
        this.range = unitEnum.range;
        this.isSleep = false;
        this.isAlert = false;
        this.isFortify = false;
        this.isFortifyUntilHealed= false;
        this.garrisonCity = null;
    }

    public static HashMap<String, HashMap<String, String>> getCombatUnitDataSheet() {
        return combatUnitDataSheet;
    }

    public static void loadDataSheet() {
        CombatUnit.combatUnitDataSheet = null; // todo Read from file
    }

    public static int getHitPointConstant() {
        return hitPointConstant;
    }

    public static void setHitPointConstant(int hitPointConstant) {
        CombatUnit.hitPointConstant = hitPointConstant;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public void setCombatStrength(int combatStrength) {
        this.combatStrength = combatStrength;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public boolean isAlert() {
        return isAlert;
    }

    public void setAlert(boolean alert) {
        isAlert = alert;
    }

    public boolean isGarrison() {
        return garrisonCity != null;
    }

    public boolean isFortify() {
        return isFortify;
    }

    public void setFortify(boolean fortify) {
        isFortify = fortify;
    }

    public boolean isFortifyUntilHealed() {
        return isFortifyUntilHealed;
    }

    public void setFortifyUntilHealed(boolean fortifyUntilHealed) {
        isFortifyUntilHealed = fortifyUntilHealed;
    }

    public City getGarrisonCity() {
        return garrisonCity;
    }

    public void setGarrisonCity(City garrisonCity) {
        this.garrisonCity = garrisonCity;
    }

    @Override
    public void makeUnitAwake() {
        super.makeUnitAwake();
        setAlert(false);
        setFortify(false);
        setFortifyUntilHealed(false);
        setGarrisonCity(null);
    }
}
