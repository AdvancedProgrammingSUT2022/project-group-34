package models.resource;

import models.tile.Feature;
import models.tile.Terrain;
import models.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public enum ResourcesLocationsDataSheet {
    Banana  (ResourceEnum.Banana,new Terrain[]{}                                                                                ,new Feature[]{Feature.Jungle}),
    Cattle  (ResourceEnum.Cattle, new Terrain[]{Terrain.Grasslands}                                                              ,new Feature[]{}),
    Deer    (ResourceEnum.Deer  , new Terrain[]{Terrain.Hills,Terrain.Tundra}                                                    ,new Feature[]{Feature.Jungle}),
    Sheep   (ResourceEnum.Sheep , new Terrain[]{Terrain.Grasslands,Terrain.Hills,Terrain.Desert,Terrain.Plains}                  ,new Feature[]{}),
    Wheat   (ResourceEnum.Wheat , new Terrain[]{Terrain.Plains}                                                                  ,new Feature[]{Feature.FloodPlain}),

    Coal    (ResourceEnum.Coal, new Terrain[]{Terrain.Grasslands,Terrain.Hills,Terrain.Plains}                                 ,new Feature[]{}),
    Horses  (ResourceEnum.Horses, new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Grasslands}                                ,new Feature[]{}),
    Iron    (ResourceEnum.Iron, new Terrain[]{Terrain.Tundra,Terrain.Desert,Terrain.Plains,Terrain.Hills,Terrain.Grasslands,Terrain.Snow}  ,new Feature[]{}),

    Cotton  (ResourceEnum.Cotton, new Terrain[]{Terrain.Plains,Terrain.Desert,Terrain.Grasslands}                                ,new Feature[]{}),
    Dyes    (ResourceEnum.Dyes, new Terrain[]{}                                                                                ,new Feature[]{Feature.Jungle,Feature.Forests}),
    Furs    (ResourceEnum.Furs, new Terrain[]{Terrain.Tundra}                                                                  ,new Feature[]{Feature.Jungle}),
    Gems    (ResourceEnum.Gems, new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Desert,Terrain.Grasslands,Terrain.Hills}  ,new Feature[]{Feature.Forests}),
    Gold    (ResourceEnum.Gold, new Terrain[]{Terrain.Plains,Terrain.Desert,Terrain.Grasslands,Terrain.Hills}                  ,new Feature[]{}),
    Eat     (ResourceEnum.Banana, new Terrain[]{Terrain.Desert,Terrain.Plains}                                                   ,new Feature[]{}),
    Ivory   (ResourceEnum.Banana, new Terrain[]{Terrain.Plains}                                                                  ,new Feature[]{}),
    Marble  (ResourceEnum.Banana, new Terrain[]{Terrain.Tundra,Terrain.Plains,Terrain.Desert,Terrain.Grasslands,Terrain.Hills}   ,new Feature[]{}),
    Silk    (ResourceEnum.Banana, new Terrain[]{}                                                                                ,new Feature[]{Feature.Jungle}),
    Silver  (ResourceEnum.Banana, new Terrain[]{Terrain.Tundra,Terrain.Desert,Terrain.Hills}                                     ,new Feature[]{}),
    Sugar   (ResourceEnum.Banana, new Terrain[]{}                                                                                ,new Feature[]{Feature.FloodPlain,Feature.Marsh})
    
    ;
    public ArrayList<Terrain> TerrainsCanContainResource;
    public ArrayList<Feature> FeaturesCanContainResource;
    public ResourceEnum resourceEnum;

    ResourcesLocationsDataSheet(ResourceEnum resourceEnum,Terrain[] terrainsCanContainResource, Feature[] featuresCanContainResource) {
        TerrainsCanContainResource.addAll(List.of(terrainsCanContainResource));
        FeaturesCanContainResource.addAll(List.of(featuresCanContainResource));
        this.resourceEnum = resourceEnum;
    }

    public static boolean whichResourceInThisTile(Tile tile){
        values().
    }

}
