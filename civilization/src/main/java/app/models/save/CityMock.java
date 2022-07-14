package app.models.save;

import app.GSave;
import app.models.City;
import app.models.tile.ImprovementEnum;

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

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
