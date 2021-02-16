package mrfiring.selectiontraining;



import android.content.Context;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ExercisesManager {
    Map<String, String> dataBase;
    long curDBVersion;

    public ExercisesManager(Context context) {
        this.curDBVersion = 0;

        this.dataBase = new HashMap<>();
    }

    @Deprecated
    public String getJsonData(String url){
        try {
            URLConnection http = new URL(url).openConnection();
            String content = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));

            String buf = "";
            while((buf = br.readLine()) != null)
                content += buf;

            br.close();

            return content.length() > 0 ? content : "null";
        }catch(Exception ex){
            ex.printStackTrace();

        }
        return "null";
    }

    public String getJsonDataFromResource(Context context, int id){
        try {
            String content = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(id)));

            String buf = "";
            while((buf = br.readLine()) != null)
                content += buf;

            br.close();

            return content.length() > 0 ? content : "null";
        }catch(Exception ex){
            ex.printStackTrace();

        }
        return "null";
    }


    @Deprecated
    private boolean saveDBtoLocalStorage(Context context ,String json, String path) {
        try {
            FileOutputStream fos = context.openFileOutput(path, 0);
            fos.write(json.getBytes());
            return true;
        }catch(FileNotFoundException ex){
            Toast.makeText(context, "Ошибка сохранения базы упражнений", Toast.LENGTH_SHORT).show();


        }catch(Exception ex){
            Toast.makeText(context, "Ошибка сохранения базы упражнений 2", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Deprecated
    public boolean loadDBfromLocalStorage(Context context, String path){
        String content = "";
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(path)));

            String buf = "";
            while((buf = br.readLine()) != null)
                content += buf;

            br.close();
            parseDBFromJson(content);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();

        }
        return false;
    }

    @Deprecated
    public void parseDBFromJson(Context context, String json, boolean saveDB , String path){

        try {
            JSONParser parser = new JSONParser();

            JSONObject obj = (JSONObject) parser.parse(json);
            long version = (long)obj.get("version");
            if(version > this.curDBVersion){
                if(saveDB && path != null)
                    saveDBtoLocalStorage(context ,json, path);

                JSONArray jsonArray = (JSONArray) obj.get("base");
                for(int i = 0; i < jsonArray.size(); i++){
                    JSONObject element =  (JSONObject) jsonArray.get(i);
                    this.dataBase.put((String)element.get("name"), (String)element.get("url"));
                }
            }



        }catch(Exception ex){
            ex.printStackTrace();

        }
    }


    public void parseDBFromJson(String json){

        try {
            JSONParser parser = new JSONParser();

            JSONObject obj = (JSONObject) parser.parse(json);
            long version = (long)obj.get("version");
            if(version > this.curDBVersion){

                JSONArray jsonArray = (JSONArray) obj.get("base");
                for(int i = 0; i < jsonArray.size(); i++){
                    JSONObject element =  (JSONObject) jsonArray.get(i);
                    this.dataBase.put((String)element.get("name"), (String)element.get("url"));
                }
            }



        }catch(Exception ex){
            ex.printStackTrace();

        }
    }

    public Map<String, String> getDataBase() {
        return dataBase;
    }

    public String getUrl(String name){
        if(this.dataBase.containsKey(name))
            return this.dataBase.get(name);
        return "page404";
    }

}
