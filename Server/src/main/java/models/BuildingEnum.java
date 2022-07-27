package models;

public enum BuildingEnum {
    Barracks("Barracks", 80, 1, TechnologyEnum.BronzeWorking),
    Granary("Granary", 100, 1, TechnologyEnum.Pottery),
    Library("Library", 80, 1, TechnologyEnum.Writing),
    Monument("Monument", 60, 1, null),
    Walls("Walls", 100, 1, TechnologyEnum.Masonry),
    WatterMill("WaterMill", 120, 2, TechnologyEnum.TheWheel),

    Armory("Armory", 130, 3, TechnologyEnum.IronWorking),
    BurialTomb("BurialTomb", 120, 0, TechnologyEnum.Philosophy),
    Circus("Circus", 150, 3, TechnologyEnum.HorsebackRiding),
    Colosseum("Colosseum", 150, 3, TechnologyEnum.Construction),
    Courthouse("Courthouse", 200, 5, TechnologyEnum.Mathematics),
    Stable("Stable", 100, 1, TechnologyEnum.HorsebackRiding),
    Temple("Temple", 120, 2, TechnologyEnum.Philosophy),

    Castle("Castle", 200, 3, TechnologyEnum.Chivalry),
    Forge("Forge", 150, 2, TechnologyEnum.MetalCasting),
    Garden("Garden", 120, 2, TechnologyEnum.Theology),
    Market("Market", 120, 0, TechnologyEnum.Currency),
    Mint("Mint", 120, 0, TechnologyEnum.Currency),
    Monastery("Monastery", 120, 2, TechnologyEnum.Theology),
    University("University", 200, 3, TechnologyEnum.Education),
    Workshop("Workshop", 100, 2, TechnologyEnum.MetalCasting),

    Bank("Bank", 220, 0, TechnologyEnum.Banking),
    MilitaryAcademy("MilitaryAcademy", 350, 3, TechnologyEnum.MilitaryScience),
    Museum("Museum", 350, 3, TechnologyEnum.Archaeology),
    OperaHouse("OperaHouse", 220, 3, TechnologyEnum.Acoustics),
    PublicSchool("PublicSchool", 350, 3, TechnologyEnum.ScientificTheory),
    SatrapsCourt("SatrapsCourt", 220, 0, TechnologyEnum.Banking),
    Theater("Theater", 300, 5, TechnologyEnum.PrintingPress),
    WindMill("WindMill", 180, 2, TechnologyEnum.Economics),

    Arsenal("Arsenal", 350, 3, TechnologyEnum.Railroad),
    BroadcastTower("BroadcastTower", 600, 3, TechnologyEnum.Radio),
    Factory("Factory", 300, 3, TechnologyEnum.SteamPower),
    Hospital("Hospital", 400, 2, TechnologyEnum.Biology),
    MilitaryBase("MilitaryBase", 450, 4, TechnologyEnum.Telegraph),
    StockExchange("StockExchange", 650, 0, TechnologyEnum.Electricity);


    private final String name;
    private final int cost;
    private final int maintenance;
    private final TechnologyEnum requiredTechnology;

    BuildingEnum(String name, int cost, int maintenance, TechnologyEnum requiredTechnology) {
        this.name = name;
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechnology = requiredTechnology;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public TechnologyEnum getRequiredTechnology() {
        return requiredTechnology;
    }

    public static BuildingEnum getBuildingEnumByName(String name) {
        for (BuildingEnum buildingEnum : values())
            if (buildingEnum.getName().equals(name)) return buildingEnum;

        return null;
    }

    public static BuildingEnum getBuildingEnumByBuilding(Building building){
        return getBuildingEnumByName(building.getName());
    }
}
