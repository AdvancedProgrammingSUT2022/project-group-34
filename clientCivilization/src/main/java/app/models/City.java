package app.models;

import app.models.tile.ImprovementEnum;

import java.util.ArrayList;

public class City {


    private String name;
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private ArrayList<ImprovementEnum> improvements = new ArrayList<>();

    private int foodRate;
    private int food;
    private int productionRate;
    private int production;
    private int scienceRate;

    private City(){}
    public static City getOneInstance(){
        return new City();
    }

    public City(String name) {
        this.name = name;
        this.citizens.add(new Citizen(null));
        this.food = 50;
        this.production = 100;
    }


    public String getName() {
        return name;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getProductionRate() {
        return productionRate;
    }
    public int getGoldRate() {
        int goldRate=0;
        for (Citizen citizen : citizens)
            if (citizen.isWorking()) goldRate += citizen.getWorkPosition().getGoldRate();

        return goldRate;
    }

    public int getFood() {
        return food;
    }

    public int getProduction() {
        return production;
    }
}
