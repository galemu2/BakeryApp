package com.udacity.bakingapp.dataMainActivity;

import android.view.View;

import org.json.JSONObject;

public interface BakeryItemClicked {

    void onItemSelected(View view, JSONObject jsonObject);
}
