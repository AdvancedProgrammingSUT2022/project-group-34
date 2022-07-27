package app.models;

import java.util.ArrayList;

public class PreGame {
    private ArrayList<User> users = new ArrayList<>();
    private int capacity;
    private int mapScale;

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
