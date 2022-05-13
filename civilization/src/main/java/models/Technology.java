package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public enum Technology {

    Agriculture("Agriculture", 20, false, false, true
            , new Technology[]{}),

    AnimalHusbandry("AnimalHusbandry", 35, false, false, false
            , new Technology[]{Agriculture}),

    Archery("Archery", 35, false, false, false
            , new Technology[]{Agriculture}),

    Mining("Mining", 35, false, false, false
            , new Technology[]{Agriculture}),

    Pottery("Pottery", 35, false, false, false
            , new Technology[]{Agriculture}),

    BronzeWorking("BronzeWorking", 55, false, false, false
            , new Technology[]{Mining}),

    Calendar("Calendar", 70, false, false, false
            , new Technology[]{Pottery}),

    Masonry("Masonry", 55, false, false, false
            , new Technology[]{Mining}),

    TheWheel("TheWheel", 55, false, false, false
            , new Technology[]{AnimalHusbandry}),

    Trapping("Trapping", 55, false, false, false
            , new Technology[]{AnimalHusbandry}),

    Writing("Writing", 55, false, false, false
            , new Technology[]{Pottery}),



    Construction("Construction", 100, false, false, false
            , new Technology[]{Masonry}),

    HorsebackRiding("HorsebackRiding", 100, false, false, false
            , new Technology[]{TheWheel}),

    IronWorking("IronWorking", 150, false, false, false
            , new Technology[]{BronzeWorking}),

    Mathematics("Mathematics", 100, false, false, false
            , new Technology[]{TheWheel,Archery}),

    Philosophy("Philosophy", 100, false, false, false
            , new Technology[]{Writing}),



    CivilService("CivilService", 400, false, false, false
            , new Technology[]{Philosophy,Trapping}),

    Currency("Currency", 250, false, false, false
            , new Technology[]{Mathematics}),

    Chivalry("Chivalry", 440, false, false, false
            , new Technology[]{CivilService,HorsebackRiding,Currency}),

    Engineering("Engineering", 250, false, false, false
            , new Technology[]{Mathematics,Construction}),

    Machinery("Machinery", 440, false, false, false
            , new Technology[]{Engineering}),

    MetalCasting("MetalCasting", 240, false, false, false
            , new Technology[]{IronWorking}),

    Physics("Physics", 440, false, false, false
            , new Technology[]{Engineering,MetalCasting}),

    Steel("Steel", 440, false, false, false
            , new Technology[]{MetalCasting}),

    Theology("Theology", 250, false, false, false
            , new Technology[]{Calendar,Philosophy}),

    Education("Education", 440, false, false, false
            , new Technology[]{Theology}),



    Acoustics("Acoustics", 650, false, false, false
            , new Technology[]{Education}),

    Archaeology("Archaeology", 1300, false, false, false
            , new Technology[]{Acoustics}),

    Banking("Banking", 650, false, false, false
            , new Technology[]{Education,Chivalry}),

    Gunpowder("Gunpowder", 680, false, false, false
            , new Technology[]{Physics,Steel}),

    Chemistry("Chemistry", 900, false, false, false
            , new Technology[]{Gunpowder}),

    PrintingPress("PrintingPress", 650, false, false, false
            , new Technology[]{Machinery,Physics}),

    Economics("Economics", 900, false, false, false
            , new Technology[]{Banking,PrintingPress}),

    Fertilize("Fertilize", 1300, false, false, false
            , new Technology[]{Chemistry}),

    Metallurgy("Metallurgy", 900, false, false, false
            , new Technology[]{Gunpowder}),

    MilitaryScience("MilitaryScience", 1300, false, false, false
            , new Technology[]{Economics,Chemistry}),

    Rifling("Rifling", 1425, false, false, false
            , new Technology[]{Metallurgy}),

    ScientificTheory("ScientificTheory", 1300, false, false, false
            , new Technology[]{Acoustics}),





    Biology("Biology", 1680, false, false, false
            , new Technology[]{Archaeology,ScientificTheory}),

    Dynamite("Dynamite", 1900, false, false, false
            , new Technology[]{Fertilize,Rifling}),

    SteamPower("SteamPower", 1680, false, false, false
            , new Technology[]{ScientificTheory,MilitaryScience}),

    Electricity("Electricity", 1900, false, false, false
            , new Technology[]{Biology,SteamPower}),

    Radio("Radio", 2200, false, false, false
            , new Technology[]{Electricity}),

    Railroad("Railroad", 1900, false, false, false
            , new Technology[]{SteamPower}),

    ReplaceableParts("ReplaceableParts", 1900, false, false, false
            , new Technology[]{SteamPower}),

    Combustion("Combustion", 2200, false, false, false
            , new Technology[]{ReplaceableParts,Railroad, Dynamite}),

    Telegraph("Telegraph", 2200, false, false, false
            , new Technology[]{Electricity}),
    ;


    public static final HashMap<String,Technology> allTechnologies = new HashMap<>();

    private String name;
    private int remainingTerm;
    private int cost; // TODO : Using cost instead of remainingTerm
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private final ArrayList<Technology> prerequisiteTechnologies;


    Technology(String name, int cost, boolean isResearching, boolean isResearchable, boolean isResearched, Technology[] prerequisiteTechnologies) {
        this.name = name;
        this.cost = cost;
        this.isResearching = isResearching;
        this.isResearchable = isResearchable;
        this.isResearched = isResearched;

        this.prerequisiteTechnologies = new ArrayList<>();
        Collections.addAll(this.prerequisiteTechnologies, prerequisiteTechnologies);

    }


    public static void createAllInstances(){
        for (Technology technology : Technology.values())
            allTechnologies.put(technology.name,technology);
    }

    public static HashMap<String, Technology> getAllTechnologiesCopy() {
        return new HashMap<>(allTechnologies);
    }

    public static Technology getTechnologyByName(String name){
        return allTechnologies.get(name);
    }






    public Technology cloneTechnology(){
        Technology technologyCopy = allTechnologies.get("Null");

        technologyCopy.setName(this.getName());
        technologyCopy.setRemainingTerm (this.getRemainingTerm());
        technologyCopy.setResearchable  (this.isResearchable());
        technologyCopy.setResearching   (this.isResearching());
        technologyCopy.setResearched    (this.isResearched());

        technologyCopy.prerequisiteTechnologies.addAll(this.prerequisiteTechnologies);

        return technologyCopy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isResearching() {
        return isResearching;
    }

    public void setResearching(boolean researching) {
        isResearching = researching;
    }

    public boolean isResearchable() {
        return isResearchable;
    }

    public void setResearchable(boolean researchable) {
        isResearchable = researchable;
    }

    public boolean isResearched() {
        return isResearched;
    }

    public void setResearched(boolean researched) {
        isResearched = researched;
    }

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public void setRemainingTerm(int remainingTerm) {
        this.remainingTerm = remainingTerm;
    }




    public ArrayList<Technology> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public void research(){
        if (isResearchable())
            setResearching(true);
    }

    public void updateTechnology(int tern){

        if (!isResearchable) {
            prerequisiteTechnologies.removeIf(prerequisiteTechnology -> prerequisiteTechnology.isResearched);
            if (prerequisiteTechnologies.isEmpty())
                isResearchable = true;
        }

        if (isResearching) {
            this.remainingTerm -= tern;
        }
        if (remainingTerm == 0) {
            isResearched = true;
            isResearching = false;
        }
    }

    public void updateTechnology(){
        updateTechnology(1);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
