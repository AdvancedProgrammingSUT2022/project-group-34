package models.unit;

import models.City;
import models.tile.Improvement;
import models.tile.Tile;

public class Work {


    private final Tile tile;
    private Worker worker;
    private int tern;

    private String type;
    private Improvement improvement = null;


    public Work(Tile tile, Worker worker, String type, int turn) {

        this.tile = tile;
        this.worker = worker;
        this.tern =turn;
        this.type = type;
        worker.startWork();
        worker.setTernWork(turn);
    }

    public Work(Tile tile, Worker worker, String type, Improvement improvement) {

        this.tile = tile;
        this.worker = worker;
        this.tern = tern;
        this.type = type;
        this.improvement = improvement;
        worker.startWork();
        worker.setTernWork(tern);
    }


    public void changeWork(Worker worker, int tern, String type){
        this.worker=worker;
        this.tern=tern;
        this.type=type;
    }


    public void startWork(Worker worker){
        this.worker = worker;
    }

    public void StopWork(){
        this.worker = null;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean update() {

        if (this.tern == 0){
            return true;
        }

        if (this.worker != null)
            this.tern--;

        return false;
    }

    public void doWork(){
        switch (this.type) {
            case "build road":
                tile.setHasRoad(true);
                break;
            case "build rail":
                tile.setHasRail(true);
                break;
            case "build improvement":
                tile.setImprovement(improvement);
                tile.getCity().addImprovement(improvement);
                break;
            case "remove feature":
                tile.deleteFeature();
                break;
            case "repair":
                tile.setLooted(false);
                break;
        }
    }

    private int getTern() {
        return 0;
    }
}
