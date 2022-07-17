package app.models.save;

public class UserMock extends Mock{

    public UserMock(Integer id) {
        super(id);
    }

    public UserMock() {
        super(0);
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }

    //todo
}
