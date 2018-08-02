package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.udacity.bakingapp.IngredientsListAdaptor;
import com.udacity.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String JSON_OBJ_BAKERY = "get-the-bakery-item-json-object";
    private static final String TAG = RecipeStepActivity.class.getSimpleName();
    private Toolbar mToolbar;

    private String jsonObjString;
    private JSONObject jsonObject;

    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";
    private static final String RECIPE_NAME = "name";
    private String recipe_name = null;

    private RecyclerView ingredientRecyclerView;
    private IngredientsListAdaptor ingredientsListAdaptor;
    private RecyclerView.LayoutManager mLayouManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent intent = getIntent();
        if (intent != null) {
            jsonObjString = intent.getStringExtra(JSON_OBJ_BAKERY);
            try {
                jsonObject = new JSONObject(jsonObjString);
                recipe_name = jsonObject.getString(RECIPE_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int size = jsonObject.length();
        Log.d(TAG, "size: " + size);

        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (recipe_name != null) actionBar.setTitle(recipe_name);
        }

        ingredientRecyclerView = findViewById(R.id.recyclerView_ingredients);
        mLayouManager = new GridLayoutManager(this, 2);
        JSONArray ingredientsJSONArray = null;

        try {
            ingredientsJSONArray = jsonObject.getJSONArray(INGREDIENTS);
        } catch (JSONException e) {
            e.printStackTrace();
        } ;

        if (ingredientsJSONArray != null) {
            ingredientsListAdaptor = new IngredientsListAdaptor(this, ingredientsJSONArray);
            ingredientRecyclerView.setLayoutManager(mLayouManager);
            ingredientRecyclerView.setAdapter(ingredientsListAdaptor);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
