package app.controllers;

import app.models.miniClass.Mini;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GLoad {

    private static GLoad gLoad;
    private ArrayList<String> temp = new ArrayList<>();

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
        String string = temp.get(id);
        //System.out.println("string :  " + string);
        try {
            //noinspection unchecked
            miniObject = (T) new Gson().fromJson(string,miniObject.getClass());
        } catch (Exception e){
            System.out.println("can not load file : " + id + ".json");
            e.printStackTrace();
        }

        return miniObject.getOriginal();
    }

    private String loadFrom(String name) {

        //System.out.println("file : " + name);
        StringBuilder stringBuilder = new StringBuilder();
        FileReader fileReader;
        try {
            fileReader = new FileReader("src/main/resources/app/save/" + name);
            int intCh;
            try {
                while ((intCh = fileReader.read()) != -1)
                    stringBuilder.append((char)intCh);
            }catch (IOException ignored){
                System.out.println("can not read");
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not found , name : " + name);
        }

        return stringBuilder.toString();
    }

    public ArrayList<String> getTemp() {
        return temp;
    }

    public void setTemp(String[] temp) {
        if (temp != null && temp.length != 0) {
            this.temp = new ArrayList<>();
            this.temp.addAll(Arrays.asList(temp));
        }
    }
}
