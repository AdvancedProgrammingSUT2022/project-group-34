package models.resource;

import models.tile.Feature;
import models.tile.Terrain;

import java.util.ArrayList;
import java.util.List;

public enum ResourcesLocationsDataSheet {
    A(new Terrain[]{},new Feature[]{}),
    
    ;
    public ArrayList<Terrain> TerrainsCanContainResource;
    public ArrayList<Feature> FeaturesCanContainResource;

    ResourcesLocationsDataSheet(Terrain[] terrainsCanContainResource, Feature[] featuresCanContainResource) {
        TerrainsCanContainResource.addAll(List.of(terrainsCanContainResource));
        FeaturesCanContainResource.addAll(List.of(featuresCanContainResource));
    }
}
