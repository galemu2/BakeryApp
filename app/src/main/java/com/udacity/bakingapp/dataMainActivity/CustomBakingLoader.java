package com.udacity.bakingapp.dataMainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;

import static com.udacity.bakingapp.utility.UtilClass.getInitialJsonArray;

public class CustomBakingLoader extends AsyncTaskLoader<JSONArray> {



    public CustomBakingLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {

        return getInitialJsonArray();
    }




}
