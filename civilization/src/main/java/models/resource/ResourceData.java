package models.resource;

import models.TechnologyEnum;
import models.tile.Improvement;
import models.tile.ImprovementEnum;

public enum ResourceData {

    Banana  (new BonusResource("Banana" ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,1)),
    Cow     (new BonusResource("Cow"    ,Improvement.getAllImprovements().get(ImprovementEnum.Pasture)    ,1)),
    Gazelle (new BonusResource("Gazelle",Improvement.getAllImprovements().get(ImprovementEnum.Camp)       ,1)),
    Sheep   (new BonusResource("Sheep"  ,Improvement.getAllImprovements().get(ImprovementEnum.Pasture)    ,2)),
    Wheat   (new BonusResource("Wheat"  ,Improvement.getAllImprovements().get(ImprovementEnum.Farm)       ,1)),

    Coal    (new StrategicResource("Coal"   ,Improvement.getAllImprovements().get(ImprovementEnum.Mine)       ,1,TechnologyEnum.ScientificTheory)),
    Horses  (new StrategicResource("Horse"  ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,1,TechnologyEnum.AnimalHusbandry)),
    Iron    (new StrategicResource("Iron"   ,Improvement.getAllImprovements().get(ImprovementEnum.Mine)       ,1,TechnologyEnum.IronWorking)),

    Cotton  (new LuxuryResource("Cotton",Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    Dye     (new LuxuryResource("Dye"   ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    Fur     (new LuxuryResource("Fur"   ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    Gemstones(new LuxuryResource("Gemstones",Improvement.getAllImprovements().get(ImprovementEnum.Camp)   ,3)),
    Gold    (new LuxuryResource("Gold"  ,Improvement.getAllImprovements().get(ImprovementEnum.Mine)       ,2)),
    Eat     (new LuxuryResource("Eat"   ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    Ivory   (new LuxuryResource("Ivory" ,Improvement.getAllImprovements().get(ImprovementEnum.Camp)       ,2)),
    Marble  (new LuxuryResource("Marble",Improvement.getAllImprovements().get(ImprovementEnum.Quarry)     ,2)),
    Silk    (new LuxuryResource("Silk"  ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    Silver  (new LuxuryResource("Silver",Improvement.getAllImprovements().get(ImprovementEnum.Mine)       ,2)),
    Sugar   (new LuxuryResource("Sugar" ,Improvement.getAllImprovements().get(ImprovementEnum.Plantation) ,2)),
    ;

    final Resource resource;

    ResourceData(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}