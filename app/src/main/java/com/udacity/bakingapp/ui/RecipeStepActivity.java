package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.udacity.bakingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String JSON_OBJ_BAKERY = "get-the-bakery-item-json-object";
    private Toolbar mToolbar;

    private String jsonObjString;
    private JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_setp);

        Intent intent = getIntent();
        if(intent!=null){
            jsonObjString = intent.getStringExtra(JSON_OBJ_BAKERY);
            try {
                jsonObject = new JSONObject(jsonObjString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

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
