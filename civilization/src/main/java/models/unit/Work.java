package models.unit;

import models.City;
import models.tile.Feature;
import models.tile.Improvement;
import models.tile.Tile;

import javax.swing.*;

public class Work {


    private City city;
    private final Tile tile;
    private Worker worker;
    private int tern;

    private String type;
    private Improvement improvement = null;

<<<<<<< HEAD
    public Work(City city, Tile tile, Worker worker, String type) {
=======
    public sWork(City city, Tile tile, Worker worker, int tern, String type) {
>>>>>>> e471f5659063c59aedd91973e4ac849ef439bd7c

        this.city = city;
        this.tile = tile;
        this.worker = worker;
        this.tern = getTern();
        this.type = type;

    }

    public Work(City city, Tile tile, Worker worker, String type, Improvement improvement) {

        this.city = city;
        this.tile = tile;
        this.worker = worker;
        this.tern = tern;
        this.type = type;
        this.improvement = improvement;

    }


    public void startWork(Worker worker){
        this.worker = worker;
    }

    public void StopWork(){
        this.worker = null;
    }

    public boolean update() {

        if (this.tern == 0){
            doWork();
            return true;
        }

        if (this.worker != null)
            this.tern--;

        return false;
    }

    public void doWork(){
        switch (this.type) {
            case "Build Road":
                tile.setHasRoad(true);
                break;
            case "Build Rail":
                tile.setHasRail(true);
                break;
            case "Build Improvement":
                tile.setImprovement(improvement);
                city.addImprovement(improvement);
                break;
            case "Remove Feature":
                tile.deleteFeature();
                break;
            case "Repair":
                tile.setLooted(false);
                break;
        }
    }

    private int getTern() {
        return 0;
    }
}
