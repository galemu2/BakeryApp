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
import android.widget.Button;
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

    int currentPos =-1;
    String s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recipe);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {


              s = bundle.getString(PASSED_JSON_ARRAY);

              currentPos = bundle.getInt(PASSED_CURRENT_POSITION);

        } else {
            Log.d(TAG, bundle+" bundle is null");
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

            VideoRecipeFragment detailFragment = new VideoRecipeFragment();
            if(s!=null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(currentPos);
                    String jSTring = jsonObject.toString();
                    Bundle b = new Bundle();
                    b.putString(PASSED_JSON_OBJ, jSTring);
                    detailFragment.setArguments(b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            try {
                videoJSONArray = new JSONArray(s);

                videoJSONObj = videoJSONArray. getJSONObject(currentPos);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frameLayout_container, detailFragment)
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
        Toast.makeText(this, "click Left", Toast.LENGTH_SHORT).show();
    }


    public void clickRight(View view) {

        Toast.makeText(this, "click right", Toast.LENGTH_SHORT).show();
    }
}
