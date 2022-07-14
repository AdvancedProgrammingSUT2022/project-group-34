package app.models.unit;

import app.models.tile.Feature;
import app.models.tile.Improvement;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;

public class Work {


    private final Tile tile;
    private Worker worker;
    private int tern;

    private String type;
    private Improvement improvement = null;


    public Work(Tile tile, Worker worker, String type, int tern) {

        this.tile = tile;
        this.worker = worker;
        this.tern =tern;
        this.type = type;
        worker.startWork();
        worker.setTernWork(tern);
    }

    public Work(Tile tile, Worker worker, String type, String improvement, int tern) {

        this.tile = tile;
        this.worker = worker;
        this.tern = tern;
        this.type = type;
        this.improvement = Improvement.getAllImprovements().get(improvement);
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
                if ((tile.getFeature().equals(Feature.Forests)|| tile.getFeature().equals(Feature.Jungle)|| tile.getFeature().equals(Feature.Marsh))&&
                        (improvement.getName().equals(ImprovementEnum.Farm.name)
                        || improvement.getName().equals(ImprovementEnum.Mine.name))
                        )
                    tile.deleteFeature();
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

    public int getTern() {
        return tern;
    }

    public Worker getWorker() {
        return worker;
    }

    public String getType() {
        return type;
    }

    public Improvement getImprovement() {
        return improvement;
    }
}
