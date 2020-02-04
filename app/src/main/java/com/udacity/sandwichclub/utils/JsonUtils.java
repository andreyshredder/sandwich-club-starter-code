package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils
{

    public static Sandwich parseSandwichJson(String json)
    {
        if (json == null || json.trim().equals(""))
            return null;

        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();


        try
        {
            JSONObject json_data = new JSONObject(json);
            JSONObject json_name = json_data.getJSONObject("name");

            mainName = json_name.getString("mainName");
            placeOfOrigin = json_data.getString("placeOfOrigin");
            description = json_data.getString("description");
            image = json_data.getString("image");

            JSONArray json_alsoKnownAs = json_name.getJSONArray("alsoKnownAs");
            if (json_alsoKnownAs != null)
                for (int i = 0; i < json_alsoKnownAs.length(); i++)
                    alsoKnownAs.add(json_alsoKnownAs.getString(i));

            JSONArray json_ingredients = json_data.getJSONArray("ingredients");
            if (json_ingredients != null)
                for (int i = 0; i < json_ingredients.length(); i++)
                    ingredients.add(json_ingredients.getString(i));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
