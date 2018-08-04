package com.udacity.bakingapp.dataRecipeActivity;

import android.content.Context;
import android.view.View;

import org.json.JSONObject;

public interface ShowSelectedStepVid {

    void onItemSelected(Context context, JSONObject jsonObject);
}
