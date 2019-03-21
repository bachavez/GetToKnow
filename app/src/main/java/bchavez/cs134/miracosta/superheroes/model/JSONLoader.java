package bchavez.cs134.miracosta.superheroes.model;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class loads Superhero data from a formatted JSON (JavaScript Object Notation) file.
 * Populates data model (Superhero) with data.
 */
public class JSONLoader {

    /**
     * Loads JSON data from a file in the assets directory.
     *
     * @param context The activity from which the data is loaded.
     * @throws IOException If there is an error reading from the JSON file.
     */
    public static List<Superhero> loadJSONFromAsset(Context context) throws IOException {
        List<Superhero> allSuperHeroes = new ArrayList<>();
        String json = null;
        InputStream is = context.getAssets().open("cs134superheroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        try {
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray allSuperHeroesJSON = jsonRootObject.getJSONArray("CS134Superheroes");

            // TODO: Loop through all the countries in the JSON data, create a Superhero
            int length = allSuperHeroesJSON.length();
            // TODO: object for each and add the object to the allSuperHeroes
            JSONObject superHeroJSON;
            Superhero superhero;
            String name, fileName, superPower, oneThing;
            for(int x = 0; x < length; x++){
                superHeroJSON = allSuperHeroesJSON.getJSONObject(x);
                //Extract the name and the region
                name = superHeroJSON.getString("Name");
                fileName = superHeroJSON.getString("FileName");
                superPower = superHeroJSON.getString("Superpower");
                oneThing = superHeroJSON.getString("OneThing");
                superhero = new Superhero(name,fileName,superPower,oneThing);
                allSuperHeroes.add(superhero);
            }


        } catch (JSONException e) {
            Log.e("Superhero Quiz", e.getMessage());
        }

        return allSuperHeroes;
    }
}

