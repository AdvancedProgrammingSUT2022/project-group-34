package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
import app.models.Citizen;
import app.models.City;
import app.models.Civilization;
import app.models.tile.Improvement;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;
import app.models.unit.Unit;

import java.util.ArrayList;

public class CityMock extends Mock {

    private String name;

    private Integer civilizationID;
    private Integer positionID;
    private int strength;
    private Integer unitUnderProductID;
    private int  unitUnderProductTern;
    private boolean isGarrison;
    private int hitPoint;

    private ArrayList<Integer> territoryID;
    private ArrayList<Integer> citizensID = new ArrayList<>();
    private ArrayList<ImprovementEnum> improvementsID = new ArrayList<>();

    private int tillNewCitizen;
    private int foodRate;
    private int food;
    private int productionRate;
    private int production;
    private int scienceRate;

    public CityMock(City city, Integer id){
        super(id);
        this.name = city.getName();
        this.strength = city.getStrength();
        this.unitUnderProductTern = city.getUnitUnderProductTern();
        this.isGarrison = city.isGarrison();
        this.hitPoint = city.getHitPoint();
        this.tillNewCitizen = city.getTillNewCitizen();
        this.foodRate = city.getFoodRate();
        this.food = city.getFood();
        this.productionRate = city.getProductionRate();
        this.production = city.getProduction();
        this.scienceRate = city.getScienceRate();

        this.civilizationID = GSave.getInstance().save(city.getCivilization());
        this.positionID = GSave.getInstance().save(city.getPosition());
        this.unitUnderProductID = GSave.getInstance().save(city.getUnitUnderProduct());

        city.getCitizens()      .forEach(citizen -> this.citizensID.add(GSave.getInstance().save(citizen)));
        city.getImprovements()  .forEach(improvement -> this.improvementsID.add(GSave.getInstance().save(improvement)));
        city.getTerritory()     .forEach(tile -> this.territoryID.add(GSave.getInstance().save(tile)));

    }

    public CityMock() {
        super(0);
    }

    @Override
    public City getOriginalObject() {
        
        City city = City.getOneInstance();
        city.setName(this.name);
        city.setCivilization((Civilization) GLoad.gIn().load(new CivilizationMock(),this.civilizationID));
        city.setPosition((Tile) GLoad.gIn().load(new TileMock(),this.positionID));
        city.setStrength(this.strength);
        city.setUnitUnderProduct((Unit) GLoad.gIn().load(new UnitMock(),this.unitUnderProductID));
        city.setUnitUnderProductTern(this.unitUnderProductTern);
        city.setGarrison(this.isGarrison);
        city.setHitPoint(this.hitPoint);

        ArrayList<Tile> territory = new ArrayList<>();
        this.territoryID.forEach(id -> territory.add((Tile) GLoad.gIn().load(new TileMock(),id)));
        city.setTerritory(territory);

        ArrayList<Citizen> citizens = new ArrayList<>();
        this.citizensID.forEach(id -> citizens.add((Citizen) GLoad.gIn().load(new CitizenMock(),id)));
        city.setCitizens(citizens);

        ArrayList<Improvement> improvements = new ArrayList<>();
        this.improvementsID.forEach(improvementEnum -> improvements.add(new Improvement(improvementEnum)));
        city.setImprovements(improvements);

        city.setTillNewCitizen(this.tillNewCitizen);
        city.setFoodRate(this.foodRate);
        city.setFood(this.food);
        city.setProductionRate(this.productionRate);
        city.setProduction(this.production);
        city.setScienceRate(this.scienceRate);

        return city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCivilizationID() {
        return civilizationID;
    }

    public void setCivilizationID(Integer civilizationID) {
        this.civilizationID = civilizationID;
    }

    public Integer getPositionID() {
        return positionID;
    }

    public void setPositionID(Integer positionID) {
        this.positionID = positionID;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Integer getUnitUnderProductID() {
        return unitUnderProductID;
    }

    public void setUnitUnderProductID(Integer unitUnderProductID) {
        this.unitUnderProductID = unitUnderProductID;
    }

    public int getUnitUnderProductTern() {
        return unitUnderProductTern;
    }

    public void setUnitUnderProductTern(int unitUnderProductTern) {
        this.unitUnderProductTern = unitUnderProductTern;
    }

    public boolean isGarrison() {
        return isGarrison;
    }

    public void setGarrison(boolean garrison) {
        isGarrison = garrison;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public ArrayList<Integer> getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(ArrayList<Integer> territoryID) {
        this.territoryID = territoryID;
    }

    public ArrayList<Integer> getCitizensID() {
        return citizensID;
    }

    public void setCitizensID(ArrayList<Integer> citizensID) {
        this.citizensID = citizensID;
    }

    public ArrayList<ImprovementEnum> getImprovementsID() {
        return improvementsID;
    }

    public void setImprovementsID(ArrayList<ImprovementEnum> improvementsID) {
        this.improvementsID = improvementsID;
    }

    public int getTillNewCitizen() {
        return tillNewCitizen;
    }

    public void setTillNewCitizen(int tillNewCitizen) {
        this.tillNewCitizen = tillNewCitizen;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getScienceRate() {
        return scienceRate;
    }

    public void setScienceRate(int scienceRate) {
        this.scienceRate = scienceRate;
    }
}
