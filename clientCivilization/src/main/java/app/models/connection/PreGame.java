package app.models.connection;

import app.models.User;

import java.util.ArrayList;

public class PreGame {
    private ArrayList<User> users = new ArrayList<>();
    private final int capacity;
    private final int mapScale;

    public PreGame(int capacity, int mapScale) {
        this.capacity = capacity;
        this.mapScale = mapScale;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<User> getUsers() {
        if (users == null) users =  new ArrayList<>();
        return users;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMapScale() {
        return mapScale;
    }

    public boolean isFull(){
        return  users.size() == capacity;
    }
}
