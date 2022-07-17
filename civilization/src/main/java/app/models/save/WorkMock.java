package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
import app.models.tile.Improvement;
import app.models.tile.Tile;
import app.models.unit.Work;
import app.models.unit.Worker;

public class WorkMock extends Mock {

    private Integer tileID;
    private Integer workerID;
    private int tern;

    private String type;
    private Improvement improvement;

    public WorkMock(Work work, Integer id) {
        super(id);
        this.tileID = GSave.getInstance().save(work.getTile());
        this.workerID = GSave.getInstance().save(work.getWorker());
        this.tern = work.getTern();
        this.type = work.getType();
        this.improvement = work.getImprovement();
    }

    public WorkMock() {
        super(0);
    }

    @Override
    public Work getOriginalObject() {

        return new Work((Tile) GLoad.getInstance().load(new TileMock(), this.tileID), (Worker) GLoad.getInstance().load(new UnitMock(),this.workerID)
                , this.type, this.improvement.getName(), this.tern);
    }
}
