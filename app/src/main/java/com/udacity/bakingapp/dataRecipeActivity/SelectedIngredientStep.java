package com.udacity.bakingapp.dataRecipeActivity;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

public interface SelectedIngredientStep {

    void onItemSelected(Context context, JSONArray jsonArray, int currentPosition);
}
