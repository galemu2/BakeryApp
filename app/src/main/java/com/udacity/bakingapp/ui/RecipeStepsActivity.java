package com.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.udacity.bakingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.udacity.bakingapp.ui.RecipeStepsFragment.FAVORITE_INGREDIENT_NAME;
import static com.udacity.bakingapp.ui.RecipeStepsFragment.JSON_OBJ_BAKERY;
import static com.udacity.bakingapp.ui.RecipeStepsFragment.selectFavIngredient;
import static java.security.AccessController.getContext;

public class RecipeStepsActivity extends AppCompatActivity {

    private static final String TAG = RecipeStepsActivity.class.getSimpleName();

    private Toolbar mToolBar;

    public static boolean mTwoPain= false;
    private String jsonObjString = null;
    private JSONObject jsonObject = null;
    private String recipe_name;

    private static final String RECIPE_NAME = "name";

    private static FragmentManager fragmentManager;
    private static ImageButton mImageButtonVew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        mTwoPain = false;

        /*
        *   source: https://stackoverflow.com/a/40523623/7504259
        *   Date:   Nov 10 '16
        *   Name:   Yazon2006
        * */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (findViewById(R.id.multi_pain_view) != null) {
            mTwoPain = true;
            Intent intent = getIntent();
            if (intent != null) {
                jsonObjString = intent.getStringExtra(JSON_OBJ_BAKERY);
                try {

                    jsonObject = new JSONObject(jsonObjString);
                    recipe_name = jsonObject.getString(RECIPE_NAME);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mToolBar = findViewById(R.id.toolBar);
                mImageButtonVew =  findViewById(R.id.image_button);
                mImageButtonVew.setVisibility(View.VISIBLE);

                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                String favBakery = sharedPreferences.getString(FAVORITE_INGREDIENT_NAME, "");
                if (recipe_name != null && favBakery.equals(recipe_name)) {
                    mImageButtonVew.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_wite_24dp, null));
                }


                setSupportActionBar(mToolBar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    if (recipe_name != null) actionBar.setTitle(recipe_name);
                }


                selectFavIngredient(mImageButtonVew, this, recipe_name);

                VideoRecipeFragment videoRecipeFragment = new VideoRecipeFragment();

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frameLayout_container, videoRecipeFragment)
                        .commit();

            } else {
                mTwoPain = false;
            }
        }
    }


        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            if (mTwoPain) {
                int id = item.getItemId();

                if (id == android.R.id.home) {
                    NavUtils.navigateUpFromSameTask(this);
                }

            }

            return super.onOptionsItemSelected(item);
        }


    public static void showSelectedItem(Uri vidUrl, String longDescription) {

        Bundle bundle = new Bundle();
        if(longDescription!=null) {
            bundle.putString(VideoRecipeFragment.PASSED_DESCRIPTION_MULTI_PAIN_VIEW, longDescription);
        }
        if(vidUrl!=null){
            String s = vidUrl.toString();
            bundle.putString(VideoRecipeFragment.PASSED_VIDEO_URL_MULTI_PAIN_VIEW, s);
        }

        VideoRecipeFragment videoRecipeFragment = new VideoRecipeFragment();
        videoRecipeFragment.setArguments(bundle);

        if(fragmentManager!=null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_container, videoRecipeFragment)
                    .commit();
        }
    }


}
