package com.udacity.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.bakingapp.database.BakeryContract;
import com.udacity.bakingapp.database.BakeryProvider;

import java.util.ArrayList;

public class BakeryWidgetService extends IntentService {

    private static final String TAG = "BakeryWidgetService";
    private static final String ACTION_UPDATE_BAKERY_WIDGETS = "com.udacity.bakingapp.widget.action.update_bakery_widgets";

    ArrayList<String> bakingIngredient = new ArrayList<>();
    String item = "";

    public BakeryWidgetService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "Action: " + action);
            if (action.equals(ACTION_UPDATE_BAKERY_WIDGETS)) {
                handleActionUpdateBakeryWidgets();
            }
        }
    }

    private void handleActionUpdateBakeryWidgets() {

        Cursor cursor = getContentResolver().query(BakeryProvider.Bakery.BAKERY,
                null,
                null,
                null,
                BakeryContract._ID);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            bakingIngredient.clear();

            int itemIndex = cursor.getColumnIndex(BakeryContract.ITEM);
            int ingredientIndex = cursor.getColumnIndex(BakeryContract.INGREDIENT);
            int quantityIndex = cursor.getColumnIndex(BakeryContract.QUANTITY);
            int unitIndex = cursor.getColumnIndex(BakeryContract.UNIT);

            item = cursor.getString(itemIndex);

//            ArrayList<String>  ingredient = new ArrayList<>();
//            ArrayList<String> quantity = new ArrayList<>();
//            ArrayList<String> unit = new ArrayList<>();

            while (cursor.moveToNext()) {
                String ingredient = cursor.getString(ingredientIndex);

                String quantity = cursor.getString(quantityIndex);

                String unit = cursor.getString(unitIndex);

                String s = quantity + " " + unit + ", " + ingredient;
                bakingIngredient.add(s);

            }
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeryWidgetProvider.class));
        BakeryWidgetProvider.updateBakeryWidgets(this, appWidgetManager, appWidgetIds, item, bakingIngredient);
    }


    public static void startActionUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, BakeryWidgetService.class);
        intent.setAction(BakeryWidgetService.ACTION_UPDATE_BAKERY_WIDGETS);
        context.startService(intent);
    }
}
