package com.udacity.bakingapp.ui;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.VideoRecipeActivity;
import com.udacity.bakingapp.dataRecipeSteps.IngredientsListAdaptor;
import com.udacity.bakingapp.dataRecipeSteps.SelectedIngredientStep;
import com.udacity.bakingapp.dataRecipeSteps.StepsListAdaptor;
import com.udacity.bakingapp.utility.UtilClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment implements SelectedIngredientStep {

    private Toolbar mToolbar;
    public static final String JSON_OBJ_BAKERY = "get-the-bakery-item-json-object";

    private String jsonObjString;
    private JSONObject jsonObject;

    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";
    private static final String RECIPE_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String VID_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";

    private String recipe_name = null;

    private RecyclerView ingredientRecyclerView;
    private IngredientsListAdaptor ingredientsListAdaptor;
    private RecyclerView.LayoutManager ingredientsLayoutManager;

    private RecyclerView stepsRecyclerView;
    private StepsListAdaptor stepsListAdaptor;
    private RecyclerView.LayoutManager stepsLayoutManager;

    //holds the steps info
    JSONArray stepsJSONArray = null;
    private String selectedItemDescription = null;
    private String vid_url = null;
    private String thumbNl_url = null;
    private Uri dscrptUrl = null;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        mToolbar = rootView.findViewById(R.id.toolBar);

        Intent intent = getAppCompatActivity(rootView).getIntent();
        if (intent != null) {
            jsonObjString = intent.getStringExtra(JSON_OBJ_BAKERY);
            try {
                jsonObject = new JSONObject(jsonObjString);
                recipe_name = jsonObject.getString(RECIPE_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        /**
         * source: https://stackoverflow.com/a/38189630/7504259
         *  date:   Jul 4, 2016
         *  name:   Mustansir */
        getAppCompatActivity(rootView).setSupportActionBar(mToolbar);
        ActionBar actionBar = getAppCompatActivity(rootView).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (recipe_name != null) actionBar.setTitle(recipe_name);
        }

        ingredientRecyclerView = rootView.findViewById(R.id.recyclerView_ingredients);
        ingredientsLayoutManager = new GridLayoutManager(getContext(), 2);
        JSONArray ingredientsJSONArray = null;

        try {
            ingredientsJSONArray = jsonObject.getJSONArray(INGREDIENTS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;

        if (ingredientsJSONArray != null) {
            ingredientsListAdaptor = new IngredientsListAdaptor(getContext(), ingredientsJSONArray);
            ingredientRecyclerView.setLayoutManager(ingredientsLayoutManager);
            ingredientRecyclerView.setAdapter(ingredientsListAdaptor);

        }

        stepsRecyclerView = rootView.findViewById(R.id.recyclerView_steps);
        stepsLayoutManager = new LinearLayoutManager(getContext());


        try {
            stepsJSONArray = jsonObject.getJSONArray(STEPS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ingredientsJSONArray != null) {
            stepsListAdaptor = new StepsListAdaptor(getContext(), stepsJSONArray, this);
            stepsRecyclerView.setLayoutManager(stepsLayoutManager);
            stepsRecyclerView.setAdapter(stepsListAdaptor);

        }

        return rootView;
    }

    private static AppCompatActivity getAppCompatActivity(View view) {
        return ((AppCompatActivity) view.getContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(getActivity());
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Context context, int currentPosition) {

        if (!RecipeStepsActivity.mTwoPain) {

            Intent intent = new Intent(context, VideoRecipeActivity.class);
            //pass the json obj as string
            intent.putExtra(VideoRecipeActivity.PASSED_JSON_ARRAY, stepsJSONArray.toString());
            //pass the adaptor position as an int
            intent.putExtra(VideoRecipeActivity.PASSED_CURRENT_POSITION, currentPosition);
            intent.putExtra(VideoRecipeActivity.RECIPE_NAME, recipe_name);
            startActivity(intent);

        } else {

            try {
                JSONObject selectedJSONObject = stepsJSONArray.getJSONObject(currentPosition);
                selectedItemDescription = selectedJSONObject.getString(DESCRIPTION);
                vid_url = selectedJSONObject.getString(VID_URL);
                thumbNl_url = selectedJSONObject.getString(THUMBNAIL_URL);
                dscrptUrl = UtilClass.getVidUri(vid_url, thumbNl_url);
            } catch (JSONException e) {
                selectedItemDescription = null;
                vid_url = null;
                thumbNl_url = null;
                dscrptUrl = null;
                e.printStackTrace();

            }

            RecipeStepsActivity.showSelectedItem(dscrptUrl, selectedItemDescription);
        }

    }
}