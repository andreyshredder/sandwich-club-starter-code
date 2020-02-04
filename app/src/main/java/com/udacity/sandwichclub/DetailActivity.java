package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity
{

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null)
        {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION)
        {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null)
        {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError()
    {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI()
    {
        // Place of origin
        TextView tv_placeOfOrigin = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.trim().equals(""))
            tv_placeOfOrigin.setText(R.string.empty_data_message);
        else
            tv_placeOfOrigin.setText(placeOfOrigin);

        // Description
        TextView tv_description = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description == null || description.trim().equals(""))
            tv_description.setText(R.string.empty_data_message);
        else
            tv_description.setText(description);

        //Also known as
        TextView tv_alsoKnowAs = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs == null || alsoKnownAs.isEmpty())
            tv_alsoKnowAs.setText(R.string.empty_data_message);
        else
            tv_alsoKnowAs.setText(TextUtils.join(System.getProperty("line.separator"), alsoKnownAs));

        //Ingredients
        TextView tv_ingredients = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients == null || ingredients.isEmpty())
            tv_ingredients.setText(R.string.empty_data_message);
        else
            tv_ingredients.setText(TextUtils.join(System.getProperty("line.separator"), ingredients));

    }
}
