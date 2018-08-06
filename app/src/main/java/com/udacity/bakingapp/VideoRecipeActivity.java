package com.udacity.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.udacity.bakingapp.VideoRecipeFragment.PASSED_JSON_OBJ;


public class VideoRecipeActivity extends AppCompatActivity {


    private static final String TAG = VideoRecipeActivity.class.getSimpleName();
    private Toolbar mToolbar;
    public static final String RECIPE_NAME = "the name-of the recipe";
    public static final String PASSED_CURRENT_POSITION = "get the current vid position";
    public static final String PASSED_JSON_ARRAY = "padded-data from recipe fragment";

    private ImageButton mButtonRight, mButtonLeft;
    private JSONArray videoJSONArray;
    private JSONObject videoJSONObj;

    int currentPos = -1;
    String s = null;
    String jSTring = null;
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    //the max number of recipe steps available
    int maxPos = -1;
    int possiblePos = -1;
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recipe);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            s = bundle.getString(PASSED_JSON_ARRAY);
            currentPos = -1;
            currentPos = bundle.getInt(PASSED_CURRENT_POSITION);
            Log.d(TAG, "current Pos from bundle: " + currentPos);

            try {
                jsonArray = new JSONArray(s);
                maxPos = jsonArray.length() - 1;

                jsonObject = jsonArray.getJSONObject(currentPos);
                jSTring = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(TAG, bundle + " bundle is null");
        }
        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        /**
         * source: https://stackoverflow.com/a/38189630/7504259
         *  date:   Jul 4, 2016
         *  name:   Mustansir */
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (bundle != null) {
                String recipeName = bundle.getString(RECIPE_NAME);
                actionBar.setTitle(recipeName);

            }
        }

        mButtonLeft = findViewById(R.id.button_left);
        mButtonRight = findViewById(R.id.button_right);

        if (savedInstanceState == null) {

            initDetailFragment();
        }


    }

    private void initDetailFragment() {
        VideoRecipeFragment detailFragment = new VideoRecipeFragment();

        Bundle b = new Bundle();

        if (jSTring != null) {
            b.putString(PASSED_JSON_OBJ, jSTring);
            detailFragment.setArguments(b);

        }

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frameLayout_container, detailFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_container, detailFragment)
                    .commit();
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

    public void clickLeft(View view) {
        moveLeftOrRight(currentPos - 1, "On first step");
    }

    public void clickRight(View view) {
        moveLeftOrRight(currentPos + 1, "On last step");
    }

    private void moveLeftOrRight(int i, String s) {
        if (0 <= currentPos && currentPos <= maxPos) {

            possiblePos = i;
        } else {
            Log.d(TAG, "current pos out of range: " + currentPos);
        }

        if (possiblePos >= 0 && possiblePos <= maxPos) {
            try {
                jsonObject = jsonArray.getJSONObject(possiblePos);
                jSTring = jsonObject.toString();
                currentPos = possiblePos;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            initDetailFragment();


        } else {
            Log.d(TAG, "possible Pos out of range: " + possiblePos);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
