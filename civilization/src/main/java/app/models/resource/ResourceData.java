package app.models.resource;

import app.models.TechnologyEnum;
import app.models.tile.ImprovementEnum;

public enum ResourceData {

    Banana  (new BonusResource("Banana" ,ImprovementEnum.Plantation ,1)),
    Cattle  (new BonusResource("Cattle" ,ImprovementEnum.Pasture    ,1)),
    Deer    (new BonusResource("Deer"   ,ImprovementEnum.Camp       ,1)),
    Sheep   (new BonusResource("Sheep"  ,ImprovementEnum.Pasture    ,2)),
    Wheat   (new BonusResource("Wheat"  ,ImprovementEnum.Farm       ,1)),

    Coal    (new StrategicResource("Coal"   ,ImprovementEnum.Mine       ,1,TechnologyEnum.ScientificTheory)),
    Horses  (new StrategicResource("Horse"  ,ImprovementEnum.Plantation ,1,TechnologyEnum.AnimalHusbandry)),
    Iron    (new StrategicResource("Iron"   ,ImprovementEnum.Mine       ,1,TechnologyEnum.IronWorking)),

    Cotton  (new LuxuryResource("Cotton",ImprovementEnum.Plantation ,2)),
    Dyes    (new LuxuryResource("Dyes"  ,ImprovementEnum.Plantation ,2)),
    Furs    (new LuxuryResource("Furs"  ,ImprovementEnum.Plantation ,2)),
    Gems    (new LuxuryResource("Gems"  ,ImprovementEnum.Camp       ,3)),
    Gold    (new LuxuryResource("Gold"  ,ImprovementEnum.Mine       ,2)),
    Incense (new LuxuryResource("Incense",ImprovementEnum.Plantation ,2)),
    Ivory   (new LuxuryResource("Ivory" ,ImprovementEnum.Camp       ,2)),
    Marble  (new LuxuryResource("Marble",ImprovementEnum.Quarry     ,2)),
    Silk    (new LuxuryResource("Silk"  ,ImprovementEnum.Plantation ,2)),
    Silver  (new LuxuryResource("Silver",ImprovementEnum.Mine       ,2)),
    Sugar   (new LuxuryResource("Sugar" ,ImprovementEnum.Plantation ,2)),
    ;

    final Resource resource;

    ResourceData(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return this.resource;
    }
}
