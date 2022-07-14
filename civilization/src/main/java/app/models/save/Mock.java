package app.models.save;

abstract class Mock {

    protected Integer id;

    public Mock(Integer id) {
        this.id = id;
    }

    public Integer getId(){
        return id;
    };

    public abstract Object getOriginalObject();
}
