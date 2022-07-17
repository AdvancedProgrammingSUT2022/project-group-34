package app.controllers;

import app.models.Game;
import app.models.save.Mock;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GLoad {

    private static GLoad gLoad;

    private GLoad(){
    }

    public static GLoad getInstance() {
        if (gLoad == null) gLoad = new GLoad();
        return gLoad;
    }

    public Game loadAllGame(){
        return GSave.getInstance().getGameSave().getOriginalObject();
    }

    public <T extends Mock> Object load(T mockObject, Integer id){
        if (id == null) return null;
        String string = loadFrom(id+".json");
        //System.out.println("string :  " + string);
        try {
            //noinspection unchecked
            mockObject = (T) new Gson().fromJson(string,mockObject.getClass());
        } catch (Exception e){
            System.out.println("can not load file : " + id + ".json");
        }
        return mockObject.getOriginalObject();
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

}
