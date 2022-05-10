package models;

import java.util.ArrayList;
import java.util.HashMap;

public enum Technology {

    Agriculture("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    AnimalHusbandry("Agriculture", 20, false, false, false
            , new Technology[]{Agriculture}
            , new Technology[]{}),
    Archery("Agriculture", 20, false, false, false
            , new Technology[]{Agriculture}
            , new Technology[]{}),
    Mining("Agriculture", 20, false, false, false
            , new Technology[]{Agriculture}
            , new Technology[]{}),
    Pottery("Agriculture", 20, false, false, false
            , new Technology[]{Agriculture}
            , new Technology[]{}),
    BronzeWorking("Agriculture", 20, false, false, false
            , new Technology[]{Mining}
            , new Technology[]{}),
    Calendar("Agriculture", 20, false, false, false
            , new Technology[]{Pottery}
            , new Technology[]{}),
    Masonry("Agriculture", 20, false, false, false
            , new Technology[]{Mining}
            , new Technology[]{}),
    TheWheel("Agriculture", 20, false, false, false
            , new Technology[]{AnimalHusbandry}
            , new Technology[]{}),
    Trapping("Agriculture", 20, false, false, false
            , new Technology[]{AnimalHusbandry}
            , new Technology[]{}),
    Writing("Agriculture", 20, false, false, false
            , new Technology[]{Pottery}
            , new Technology[]{}),



    Construction("Agriculture", 20, false, false, false
            , new Technology[]{Masonry}
            , new Technology[]{}),
    HorsebackRiding("Agriculture", 20, false, false, false
            , new Technology[]{TheWheel}
            , new Technology[]{}),
    IronWorking("Agriculture", 20, false, false, false
            , new Technology[]{BronzeWorking}
            , new Technology[]{}),
    Mathematics("Agriculture", 20, false, false, false
            , new Technology[]{TheWheel,Archery}
            , new Technology[]{}),
    Philosophy("Agriculture", 20, false, false, false
            , new Technology[]{Writing}
            , new Technology[]{}),



    CivilService("Agriculture", 20, false, false, false
            , new Technology[]{Philosophy,Trapping}
            , new Technology[]{}),
    Chivalry("Agriculture", 20, false, false, false
            , new Technology[]{CivilService,HorsebackRiding,Currency}
            , new Technology[]{}),
    Currency("Agriculture", 20, false, false, false
            , new Technology[]{Mathematics}
            , new Technology[]{}),
    Education("Agriculture", 20, false, false, false
            , new Technology[]{Theology}
            , new Technology[]{}),
    Engineering("Agriculture", 20, false, false, false
            , new Technology[]{Mathematics,Construction}
            , new Technology[]{}),
    Machinery("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    MetalCasting("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    Physics("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    Steel("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    Theology("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),



    Acoustics("Agriculture", 20, false, false, false
            , new Technology[]{Education}
            , new Technology[]{}),
    Archaeology("Agriculture", 20, false, false, false
            , new Technology[]{Acoustics}
            , new Technology[]{}),
    Banking("Agriculture", 20, false, false, false
            , new Technology[]{Education,Chivalry}
            , new Technology[]{}),
    Gunpowder("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    Chemistry("Agriculture", 20, false, false, false
            , new Technology[]{Gunpowder}
            , new Technology[]{}),
    Economics("Agriculture", 20, false, false, false
            , new Technology[]{Banking,PrintingPress}
            , new Technology[]{}),
    Fertilizer("Agriculture", 20, false, false, false
            , new Technology[]{Chemistry}
            , new Technology[]{}),
    Metallurgy("Agriculture", 20, false, false, false
            , new Technology[]{Gunpowder}
            , new Technology[]{}),
    MilitaryScience("Agriculture", 20, false, false, false
            , new Technology[]{Economics,Chemistry}
            , new Technology[]{}),
    PrintingPress("Agriculture", 20, false, false, false
            , new Technology[]{Machinery,Physics}
            , new Technology[]{}),
    Rifling("Agriculture", 20, false, false, false
            , new Technology[]{Metallurgy}
            , new Technology[]{}),
    ScientificTheory("Agriculture", 20, false, false, false
            , new Technology[]{Acoustics}
            , new Technology[]{}),
    Biology("Agriculture", 20, false, false, false
            , new Technology[]{Archaeology,ScientificTheory}
            , new Technology[]{}),
    Dynamic("Agriculture", 20, false, false, false
            , new Technology[]{Fertilizer,Rifling}
            , new Technology[]{}),
    Electricity("Agriculture", 20, false, false, false
            , new Technology[]{Biology,SteamPower}
            , new Technology[]{}),
    SteamPower("Agriculture", 20, false, false, false
            , new Technology[]{ScientificTheory,MilitaryScience}
            , new Technology[]{}),
    Railroad("Agriculture", 20, false, false, false
            , new Technology[]{SteamPower}
            , new Technology[]{}),
    Replaceable("Agriculture", 20, false, false, false
            , new Technology[]{SteamPower}
            , new Technology[]{}),
    Radio("Agriculture", 20, false, false, false
            , new Technology[]{Electricity}
            , new Technology[]{}),
    Parts("Agriculture", 20, false, false, false
            , new Technology[]{}
            , new Technology[]{}),
    Combustion("Agriculture", 20, false, false, false
            , new Technology[]{Replaceable,Parts,Railroad,Dynamic}
            , new Technology[]{}),
    Telegraph("Agriculture", 20, false, false, false
            , new Technology[]{Electricity}
            , new Technology[]{}),
    ;


    public static HashMap<String,HashMap<String, String>> dataSheet = new HashMap<>();
    public static HashMap<String,Technology> allTechnologies = new HashMap<>();

    private String name;
    private int remainingTerm;
    private int cost; // TODO : Using cost instead of remainingTerm
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private ArrayList<Technology> prerequisiteTechnologies;
    private ArrayList<Technology> relatedTechnologies;


    Technology(String name, int cost, boolean isResearching, boolean isResearchable, boolean isResearched, Technology[] prerequisiteTechnologies, Technology[] relatedTechnologies) {
        this.name = name;
        this.cost = cost;
        this.isResearching = isResearching;
        this.isResearchable = isResearchable;
        this.isResearched = isResearched;

        this.prerequisiteTechnologies = new ArrayList<>();
        for (Technology technology : prerequisiteTechnologies)
            this.prerequisiteTechnologies.add(technology);

        this.relatedTechnologies = new ArrayList<>();
        for (Technology technology : relatedTechnologies)
            this.relatedTechnologies.add(technology);

    }

    public static void loadDataSheet(){
        //todo : ReadFromFile
    }

    public static int createAllInstances(){

    }

    public static HashMap<String, Technology> getAllTechnologiesCopy() {

        HashMap<String, Technology> allTechnologiesCopy = new HashMap<>();

        allTechnologies.forEach((name,technology)->{
            Technology technologyCopy = technology.clone();
            allTechnologiesCopy.put(name,technologyCopy);
        });

        return allTechnologiesCopy;
    }






    public Technology clone(){
        Technology technologyCopy = new Technology(dataSheet.get("Null"));

        technologyCopy.setName(this.getName());
        technologyCopy.setRemainingTerm (this.getRemainingTerm());
        technologyCopy.setResearchable  (this.isResearchable());
        technologyCopy.setResearching   (this.isResearching());
        technologyCopy.setResearched    (this.isResearched());

        for (String prerequisiteTechnology  : this.prerequisiteTechnologies)
            technologyCopy.prerequisiteTechnologies.add(prerequisiteTechnology);

        for (String relatedTechnology       : this.relatedTechnologies)
            technologyCopy.relatedTechnologies.add(relatedTechnology);

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

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public void setRemainingTerm(int remainingTerm) {
        this.remainingTerm = remainingTerm;
    }

    public boolean isResearched() {
        return isResearched;
    }

    public void setResearched(boolean researched) {
        isResearched = researched;
    }




    public ArrayList<String> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public ArrayList<String> getRelatedTechnologies() {
        return relatedTechnologies;
    }




    public void removePrerequisiteTechnology(String technologyName) {
        this.prerequisiteTechnologies.remove(technologyName);
        if (this.prerequisiteTechnologies.isEmpty())
            setResearchable(true);
    }

    public void research(){
        if (isResearchable())
            setResearching(true);
    }

    public void updateTechnology(int tern){
        if (isResearching)
            this.remainingTerm--;

        if (getRemainingTerm() == 0)
            setResearched(true);
            setResearching(false);
    }

    public void updateTechnology(){
        updateTechnology(1);
    }


    public Technology getTechnologyByName(String name){
        return allTechnologies.get(name);
    }



}
