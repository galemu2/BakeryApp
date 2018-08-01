package com.udacity.bakingapp.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.bakingapp.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import static com.udacity.bakingapp.ui.MainActivity.URL_BASE;

public class CustomBakingLoader extends AsyncTaskLoader<JSONArray> {


    private JSONArray bakingArray;

    public CustomBakingLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {

        try {

            try {
                bakingArray = new JSONArray(MainActivity.runner(URL_BASE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bakingArray;
    }



}
