package app.controllers;

import app.models.miniClass.Mini;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GLoad {

    private static GLoad gLoad;
    private ArrayList<String> temp = new ArrayList<>();
    private HashMap<Integer,Object> tempObject = new HashMap<>();

    private GLoad(){
    }

    public static GLoad getInstance() {
        if (gLoad == null) gLoad = new GLoad();
        return gLoad;
    }

    public <T extends Mini> Object loadObject(T object, int id){
        return load(object, id);
    }

    public <T extends Mini> Object load(T miniObject, Integer id){
        if (id == null) return null;
        if (tempObject.containsKey(id)) return tempObject.get(id);

        String string = temp.get(id);
        try {
            //noinspection unchecked
            miniObject = (T) new Gson().fromJson(string,miniObject.getClass());
        } catch (Exception e){
            System.out.println("can not load file : " + id + ".json");
            e.printStackTrace();
        }
        Object object = miniObject.getOriginal();
        tempObject.put(id, object);
        return object;
    }

    public ArrayList<String> getTemp() {
        return temp;
    }

    public void setTemp(String[] temp) {
        if (temp != null && temp.length != 0) {
            this.temp = new ArrayList<>();
            this.temp.addAll(Arrays.asList(temp));
            this.tempObject.clear();
        }
    }
}
