package app.models.save;

import app.GSave;
import app.models.tile.Improvement;
import app.models.unit.Work;

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

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
