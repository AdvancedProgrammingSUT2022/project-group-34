package models;

import java.util.ArrayList;
import java.util.Collections;

public enum TechnologyEnum {

    Agriculture("Agriculture", 20, false, false, true
                        , new String[]{}),

    AnimalHusbandry("AnimalHusbandry", 35, false, false, false
                            , new String[]{"Agriculture"}),

    Archery("Archery", 35, false, false, false
                    , new String[]{"Agriculture"}),

    Mining("Mining", 35, false, false, false
                   , new String[]{"Agriculture"}),

    Pottery("Pottery", 35, false, false, false
                    , new String[]{"Agriculture"}),

    BronzeWorking("BronzeWorking", 55, false, false, false
                          , new String[]{"Mining"}),

    Calendar("Calendar", 70, false, false, false
                     , new String[]{"Pottery"}),

    Masonry("Masonry", 55, false, false, false
                    , new String[]{"Mining"}),

    TheWheel("TheWheel", 55, false, false, false
                     , new String[]{"AnimalHusbandry"}),

    Trapping("Trapping", 55, false, false, false
                     , new String[]{"AnimalHusbandry"}),

    Writing("Writing", 55, false, false, false
                    , new String[]{"Pottery"}),



    Construction("Construction", 100, false, false, false
                         , new String[]{"Masonry"}),

    HorsebackRiding("HorsebackRiding", 100, false, false, false
                            , new String[]{"TheWheel"}),

    IronWorking("IronWorking", 150, false, false, false
                        , new String[]{"BronzeWorking"}),

    Mathematics("Mathematics", 100, false, false, false
                        , new String[]{"TheWheel","Archery"}),

    Philosophy("Philosophy", 100, false, false, false
                       , new String[]{"Writing"}),



    CivilService("CivilService", 400, false, false, false
                         , new String[]{"Philosophy","Trapping"}),

    Currency("Currency", 250, false, false, false
                     , new String[]{"Mathematics"}),

    Chivalry("Chivalry", 440, false, false, false
                     , new String[]{"CivilService","HorsebackRiding","Currency"}),

    Engineering("Engineering", 250, false, false, false
                        , new String[]{"Mathematics","Construction"}),

    Machinery("Machinery", 440, false, false, false
                      , new String[]{"Engineering"}),

    MetalCasting("MetalCasting", 240, false, false, false
                         , new String[]{"IronWorking"}),

    Physics("Physics", 440, false, false, false
                    , new String[]{"Engineering","MetalCasting"}),

    Steel("Steel", 440, false, false, false
                  , new String[]{"MetalCasting"}),

    Theology("Theology", 250, false, false, false
                     , new String[]{"Calendar","Philosophy"}),

    Education("Education", 440, false, false, false
                      , new String[]{"Theology"}),



    Acoustics("Acoustics", 650, false, false, false
                      , new String[]{"Education"}),

    Archaeology("Archaeology", 1300, false, false, false
                        , new String[]{"Acoustics"}),

    Banking("Banking", 650, false, false, false
                    , new String[]{"Education","Chivalry"}),

    Gunpowder("Gunpowder", 680, false, false, false
                      , new String[]{"Physics","Steel"}),

    Chemistry("Chemistry", 900, false, false, false
                      , new String[]{"Gunpowder"}),

    PrintingPress("PrintingPress", 650, false, false, false
                          , new String[]{"Machinery","Physics"}),

    Economics("Economics", 900, false, false, false
                      , new String[]{"Banking","PrintingPress"}),

    Fertilize("Fertilize", 1300, false, false, false
                      , new String[]{"Chemistry"}),

    Metallurgy("Metallurgy", 900, false, false, false
                       , new String[]{"Gunpowder"}),

    MilitaryScience("MilitaryScience", 1300, false, false, false
                            , new String[]{"Economics","Chemistry"}),

    Rifling("Rifling", 1425, false, false, false
                    , new String[]{"Metallurgy"}),

    ScientificTheory("ScientificTheory", 1300, false, false, false
                             , new String[]{"Acoustics"}),





    Biology("Biology", 1680, false, false, false
                    , new String[]{"Archaeology","ScientificTheory"}),

    Dynamite("Dynamite", 1900, false, false, false
                     , new String[]{"Fertilize","Rifling"}),

    SteamPower("SteamPower", 1680, false, false, false
                       , new String[]{"ScientificTheory","MilitaryScience"}),

    Electricity("Electricity", 1900, false, false, false
                        , new String[]{"Biology","SteamPower"}),

    Radio("Radio", 2200, false, false, false
                  , new String[]{"Electricity"}),

    Railroad("Railroad", 1900, false, false, false
                     , new String[]{"SteamPower"}),

    ReplaceableParts("ReplaceableParts", 1900, false, false, false
                             , new String[]{"SteamPower"}),

    Combustion("Combustion", 2200, false, false, false
                       , new String[]{"ReplaceableParts","Railroad","Dynamite"}),

    Telegraph("Telegraph", 2200, false, false, false
                      , new String[]{"Electricity"})
    ;


    private final String name;
    private final int remainingTerm;
    private final int cost; // TODO : Using cost instead of remainingTerm
    private final boolean isResearching;
    private final boolean isResearchable;
    private final boolean isResearched;
    private final ArrayList<String> prerequisiteTechnologies;

    TechnologyEnum(String name, int cost, boolean isResearching, boolean isResearchable, boolean isResearched, String[] prerequisiteTechnologies) {
        this.name = name;
        this.cost = cost;
        this.isResearching = isResearching;
        this.isResearchable = isResearchable;
        this.isResearched = isResearched;
        this.prerequisiteTechnologies = new ArrayList<>();
        Collections.addAll(this.prerequisiteTechnologies, prerequisiteTechnologies);
        this.remainingTerm = 0;
    }

    public String getName() {
        return name;
    }

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public int getCost() {
        return cost;
    }

    public boolean isResearching() {
        return isResearching;
    }

    public boolean isResearchable() {
        return isResearchable;
    }

    public boolean isResearched() {
        return isResearched;
    }

    public ArrayList<TechnologyEnum> getPrerequisiteTechnologies() {
        ArrayList<TechnologyEnum> technologyEnums = new ArrayList<>();
        for (String technologyName : prerequisiteTechnologies) {
            for (TechnologyEnum technologyEnum : values()) {
                if (technologyEnum.name.equals(technologyName)){
                    technologyEnums.add(technologyEnum);
                    break;
                }
            }
        }
        return technologyEnums;
    }
}
