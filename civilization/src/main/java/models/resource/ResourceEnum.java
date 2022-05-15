package models.resource;

import models.TechnologyEnum;
import models.tile.Improvement;

public enum ResourceEnum {

    Banana  (new BonusResource("Banana" ,Improvement.Plantation ,1)),
    Cow     (new BonusResource("Cow"    ,Improvement.Pasture    ,1)),
    Gazelle (new BonusResource("Gazelle",Improvement.Camp       ,1)),
    Sheep   (new BonusResource("Sheep"  ,Improvement.Pasture    ,2)),
    Wheat   (new BonusResource("Wheat"  ,Improvement.Farm       ,1)),

    Coal    (new StrategicResource("Coal"   ,Improvement.Mine       ,1,TechnologyEnum.ScientificTheory)),
    Horses  (new StrategicResource("Horse"  ,Improvement.Plantation ,1,TechnologyEnum.AnimalHusbandry)),
    Iron    (new StrategicResource("Iron"   ,Improvement.Mine       ,1,TechnologyEnum.IronWorking)),

    Cotton  (new LuxuryResource("Cotton",Improvement.Plantation ,2)),
    Dye     (new LuxuryResource("Dye"   ,Improvement.Plantation ,2)),
    Fur     (new LuxuryResource("Fur"   ,Improvement.Plantation ,2)),
    Gemstones(new LuxuryResource("Gemstones",Improvement.Camp   ,3)),
    Gold    (new LuxuryResource("Gold"  ,Improvement.Mine       ,2)),
    Eat     (new LuxuryResource("Eat"   ,Improvement.Plantation ,2)),
    Ivory   (new LuxuryResource("Ivory" ,Improvement.Camp       ,2)),
    Marble  (new LuxuryResource("Marble",Improvement.Quarry     ,2)),
    Silk    (new LuxuryResource("Silk"  ,Improvement.Plantation ,2)),
    Silver  (new LuxuryResource("Silver",Improvement.Mine       ,2)),
    Sugar   (new LuxuryResource("Sugar" ,Improvement.Plantation ,2)),
    ;

    Resource resource;

    ResourceEnum(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
