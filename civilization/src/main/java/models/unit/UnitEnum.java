package models.unit;

import models.TechnologyEnum;
import models.resource.ResourceData;

public enum UnitEnum {

    Archer          ("Archer"   ,70, "Archery"  ,4 ,6 ,2,2,null , TechnologyEnum.Archery    ,false),
    ChariotArcher   ("ChariotArcher",70, "Mounted",3,6,2,4, ResourceData.Horses, TechnologyEnum.TheWheel     ,false),
    Scout           ("Scout"    ,70, "Recon"    ,4 ,0 ,0,2,null , null      ,false),
    Settler         ("Settler"  ,70, "Civilian" ,0 ,0 ,0,2,null , null      ,false),
    Spearman        ("Spearman" ,70, "Melee"    ,7 ,0 ,0,2,null , TechnologyEnum.BronzeWorking,false),
    Warrior         ("Warrior"  ,70, "Melee"    ,6 ,0 ,0,2,null , null      ,false),
    Worker          ("Worker"   ,70, "Civilian" ,0 ,0 ,0,2,null , null      ,false),
    Catapult        ("Catapult" ,70, "Siege"    ,4 ,14,2,2, ResourceData.Iron    , TechnologyEnum.Mathematics,true),
    Horseman        ("Horseman" ,70, "Mounted"  ,12,0 ,0,4, ResourceData.Horses  , TechnologyEnum.HorsebackRiding,false),
    Swordsman       ("Swordsman",70, "Melee"    ,11,0 ,0,2, ResourceData.Iron    , TechnologyEnum.IronWorking,false),
    Crossbowman     ("Crossbowman",70, "Archery",6 ,12,2,2,null , TechnologyEnum.Machinery  ,false),
    Knight          ("Knight"   ,70, "Mounted"  ,18,0 ,0,3, ResourceData.Horses  , TechnologyEnum.Chivalry   ,false),
    Longswordsman   ("Longswordsman",70, "Melee",18,0 ,0,3, ResourceData.Iron    , TechnologyEnum.Steel      ,false),
    Pikeman         ("Pikeman"  ,70, "Melee"    ,10,0 ,0,2,null , TechnologyEnum.CivilService,false),
    Trebuchet       ("Trebuchet",70, "Siege"    ,6 ,20,0,2, ResourceData.Iron    , TechnologyEnum.Physics    ,true),
    Canon           ("Canon"    ,70, "Siege"    ,10,26,2,2,null, TechnologyEnum.Chemistry   ,true),
    Cavalry         ("Cavalry"  ,70, "Mounted"  ,25,0 ,2,3, ResourceData.Horses, TechnologyEnum.MilitaryScience,false),
    Lancer          ("Lancer"   ,70, "Mounted"  ,22,0 ,0,4, ResourceData.Horses, TechnologyEnum.Metallurgy   ,false),
    Musketman       ("Musketman",70, "Gunpowder",16,0 ,0,2,null, TechnologyEnum.Gunpowder   ,false),
    Rifleman        ("Rifleman" ,70, "Gunpowder",25,0 ,0,2,null, TechnologyEnum.Rifling     ,false),
    AntiTankGun     ("AntiTankGun",70, "Gunpowder",32,0,0,2,null, TechnologyEnum.ReplaceableParts,false),
    Artillery       ("Artillery",70, "Siege"    ,16,32,0,2,null, TechnologyEnum.Dynamite    ,true),
    Infantry        ("Infantry" ,70, "Gunpowder",36,0 ,3,2,null, TechnologyEnum.ReplaceableParts,false),
    Panzer          ("Panzer"   ,70, "Armored"  ,60,0 ,0,5,null, TechnologyEnum.Combustion  ,false),
    Tank            ("Tank"     ,70, "Armored"  ,60,0 ,0,4,null, TechnologyEnum.Combustion  ,false),
    ;


    final String name;
    final int cost;
    final String combatType;
    final int combatStrength;
    final int rangedCombatStrength;
    final int range;
    final int movement;
    final TechnologyEnum requiredTechnology;
    final ResourceData requiredResource;
    final boolean isSiegeTool;

    UnitEnum(String name, int cost, String combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourceData requiredResource, TechnologyEnum requiredTechnology, boolean isSiegeTool) {
        this.name = name;
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.requiredTechnology = requiredTechnology;
        this.requiredResource = requiredResource;
        this.isSiegeTool = isSiegeTool;
    }

    public TechnologyEnum getRequiredTechnology() {
        return requiredTechnology;
    }

    public ResourceData getRequiredResource() {
        return requiredResource;
    }
}
