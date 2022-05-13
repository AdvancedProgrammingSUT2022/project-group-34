package models;

import java.util.ArrayList;
import java.util.HashMap;

public enum Technology {

    Agriculture("Agriculture", 20, false, false, true
            , new Technology[]{}
            , new String[]{"Pottery","AnimalHusbandry","Archery","Mining"}),
    AnimalHusbandry("Agriculture", 35, false, false, false
            , new Technology[]{Agriculture}
            , new String[]{"Trapping","TheWheel"}),
    Archery("Agriculture", 35, false, false, false
            , new Technology[]{Agriculture}
            , new String[]{"Mathematics"}),
    Mining("Agriculture", 35, false, false, false
            , new Technology[]{Agriculture}
            , new String[]{"Masonry","BronzeWorking"}),
    Pottery("Agriculture", 35, false, false, false
            , new Technology[]{Agriculture}
            , new String[]{Calendar,Writing}),
    BronzeWorking("Agriculture", 55, false, false, false
            , new Technology[]{Mining}
            , new String[]{IronWorking}),
    Calendar("Agriculture", 70, false, false, false
            , new Technology[]{Pottery}
            , new String[]{Theology}),
    Masonry("Agriculture", 55, false, false, false
            , new Technology[]{Mining}
            , new String[]{Construction}),
    TheWheel("Agriculture", 55, false, false, false
            , new Technology[]{AnimalHusbandry}
            , new String[]{HorsebackRiding,Mathematics}),
    Trapping("Agriculture", 55, false, false, false
            , new Technology[]{AnimalHusbandry}
            , new String[]{CivilService}),
    Writing("Agriculture", 55, false, false, false
            , new Technology[]{Pottery}
            , new String[]{Philosophy}),



    Construction("Agriculture", 100, false, false, false
            , new Technology[]{Masonry}
            , new String[]{Engineering}),
    HorsebackRiding("Agriculture", 100, false, false, false
            , new Technology[]{TheWheel}
            , new String[]{Chivalry}),
    IronWorking("Agriculture", 150, false, false, false
            , new Technology[]{BronzeWorking}
            , new String[]{MetalCasting}),
    Mathematics("Agriculture", 100, false, false, false
            , new Technology[]{TheWheel,Archery}
            , new String[]{Currency,Engineering}),
    Philosophy("Agriculture", 100, false, false, false
            , new Technology[]{Writing}
            , new String[]{Theology,CivilService}),



    CivilService("Agriculture", 400, false, false, false
            , new Technology[]{Philosophy,Trapping}
            , new String[]{Chivalry}),
    Currency("Agriculture", 250, false, false, false
            , new Technology[]{Mathematics}
            , new String[]{Chivalry}),
    Chivalry("Agriculture", 440, false, false, false
            , new Technology[]{CivilService,HorsebackRiding,Currency}
            , new String[]{Banking}),
    Education("Agriculture", 440, false, false, false
            , new Technology[]{Theology}
            , new String[]{Acoustics,Banking}),
    Engineering("Agriculture", 250, false, false, false
            , new Technology[]{Mathematics,Construction}
            , new String[]{Machinery,Banking}),
    Machinery("Agriculture", 440, false, false, false
            , new Technology[]{Engineering}
            , new String[]{PrintingPress}),
    MetalCasting("Agriculture", 240, false, false, false
            , new Technology[]{IronWorking}
            , new String[]{Physics,Steel}),
    Physics("Agriculture", 440, false, false, false
            , new Technology[]{Engineering,MetalCasting}
            , new String[]{PrintingPress,Gunpowder}),
    Steel("Agriculture", 440, false, false, false
            , new Technology[]{MetalCasting}
            , new String[]{Gunpowder}),
    Theology("Agriculture", 250, false, false, false
            , new Technology[]{Calendar,Philosophy}
            , new String[]{Education}),



    Acoustics("Agriculture", 650, false, false, false
            , new Technology[]{Education}
            , new String[]{ScientificTheory}),
    Archaeology("Agriculture", 1300, false, false, false
            , new Technology[]{Acoustics}
            , new String[]{Biology}),
    Banking("Agriculture", 650, false, false, false
            , new Technology[]{Education,Chivalry}
            , new String[]{Economics}),
    Gunpowder("Agriculture", 680, false, false, false
            , new Technology[]{Physics,Steel}
            , new String[]{Chemistry,Metallurgy}),
    Chemistry("Agriculture", 900, false, false, false
            , new Technology[]{Gunpowder}
            , new String[]{MilitaryScience, Fertilize}),
    Economics("Agriculture", 900, false, false, false
            , new Technology[]{Banking,PrintingPress}
            , new String[]{MilitaryScience}),
    Fertilize("Agriculture", 1300, false, false, false
            , new Technology[]{Chemistry}
            , new String[]{Dynamite}),
    Metallurgy("Agriculture", 900, false, false, false
            , new Technology[]{Gunpowder}
            , new String[]{Rifling}),
    MilitaryScience("Agriculture", 1300, false, false, false
            , new Technology[]{Economics,Chemistry}
            , new String[]{SteamPower}),
    PrintingPress("Agriculture", 650, false, false, false
            , new Technology[]{Machinery,Physics}
            , new String[]{Economics}),
    Rifling("Agriculture", 1425, false, false, false
            , new Technology[]{Metallurgy}
            , new String[]{Dynamite}),
    ScientificTheory("Agriculture", 1300, false, false, false
            , new Technology[]{Acoustics}
            , new String[]{Biology,SteamPower}),




    Biology("Agriculture", 1680, false, false, false
            , new Technology[]{Archaeology,ScientificTheory}
            , new String[]{Electricity}),
    Dynamite("Agriculture", 1900, false, false, false
            , new Technology[]{Fertilize,Rifling}
            , new String[]{}),
    SteamPower("Agriculture", 1680, false, false, false
            , new Technology[]{ScientificTheory,MilitaryScience}
            , new String[]{Electricity,ReplaceableParts,Railroad}),
    Electricity("Agriculture", 1900, false, false, false
            , new Technology[]{Biology,SteamPower}
            , new String[]{Telegraph,Radio}),
    Radio("Agriculture", 2200, false, false, false
            , new Technology[]{Electricity}
            , new String[]{}),
    Railroad("Agriculture", 1900, false, false, false
            , new Technology[]{SteamPower}
            , new String[]{Combustion}),
    ReplaceableParts("Agriculture", 1900, false, false, false
            , new Technology[]{SteamPower}
            , new String[]{Combustion}),
    Combustion("Agriculture", 2200, false, false, false
            , new Technology[]{ReplaceableParts,Railroad, Dynamite}
            , new String[]{}),
    Telegraph("Agriculture", 2200, false, false, false
            , new Technology[]{Electricity}
            , new String[]{}),
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
    private ArrayList<String> relatedTechnologies;


    Technology(String name, int cost, boolean isResearching, boolean isResearchable, boolean isResearched, Technology[] prerequisiteTechnologies, String[] relatedTechnologies) {
        this.name = name;
        this.cost = cost;
        this.isResearching = isResearching;
        this.isResearchable = isResearchable;
        this.isResearched = isResearched;

        this.prerequisiteTechnologies = new ArrayList<>();
        for (Technology technology : prerequisiteTechnologies)
            this.prerequisiteTechnologies.add(technology);

        this.relatedTechnologies = new ArrayList<>();
        for (String technology : relatedTechnologies)
            this.relatedTechnologies.add(technology);

    }

    public static void loadDataSheet(){
        //todo : ReadFromFile
    }

    public static void createAllInstances(){

    }

    public static HashMap<String, Technology> getAllTechnologiesCopy() {

        HashMap<String, Technology> allTechnologiesCopy = new HashMap<>();

        allTechnologies.forEach((name,technology)->{
            Technology technologyCopy = technology.cloneTechnology();
            allTechnologiesCopy.put(name,technologyCopy);
        });

        return allTechnologiesCopy;
    }






    public Technology cloneTechnology(){
        Technology technologyCopy = allTechnologies.get("Null");

        technologyCopy.setName(this.getName());
        technologyCopy.setRemainingTerm (this.getRemainingTerm());
        technologyCopy.setResearchable  (this.isResearchable());
        technologyCopy.setResearching   (this.isResearching());
        technologyCopy.setResearched    (this.isResearched());

        for (Technology prerequisiteTechnology  : this.prerequisiteTechnologies)
            technologyCopy.prerequisiteTechnologies.add(prerequisiteTechnology);

        for (Technology relatedTechnology       : this.relatedTechnologies)
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




    public ArrayList<Technology> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public ArrayList<Technology> getRelatedTechnologies() {
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
