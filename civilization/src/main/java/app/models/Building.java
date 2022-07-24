package app.models;

import java.util.HashMap;

public class Building {
    private static HashMap<BuildingEnum, Building> allBuildings;

    private String name;
    private int cost;
    private int maintenance;
    private TechnologyEnum requiredTechnology;

    public Building(BuildingEnum buildingEnum) {
        this.name = buildingEnum.getName();
        this.cost = buildingEnum.getCost();
        this.maintenance = buildingEnum.getMaintenance();
        this.requiredTechnology = buildingEnum.getRequiredTechnology();
    }

    private static void createAllInstances() {
        for (BuildingEnum buildingEnum : BuildingEnum.values())
            allBuildings.put(buildingEnum, new Building(buildingEnum));
    }

    public static HashMap<BuildingEnum, Building> getAllBuildings() {
        if (allBuildings == null)
            createAllInstances();

        return new HashMap<>(allBuildings);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(int maintenance) {
        this.maintenance = maintenance;
    }

    public TechnologyEnum getRequiredTechnology() {
        return requiredTechnology;
    }

    public void setRequiredTechnology(TechnologyEnum requiredTechnology) {
        this.requiredTechnology = requiredTechnology;
    }
}
