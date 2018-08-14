package com.udacity.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.bakingapp.database.BakeryContract;
import com.udacity.bakingapp.database.BakeryProvider;

import java.util.ArrayList;

public class BakeryWidgetService extends IntentService {

    private static final String TAG = "BakeryWidgetService";
    private static final String ACTION_UPDATE_BAKERY_WIDGETS = "com.udacity.bakingapp.widget.action.update_bakery_widgets";

    ArrayList<String> bakingIngredient = new ArrayList<>();
    String item = null;


    public BakeryWidgetService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        new AsyncTask () {
            @Override
            protected Object doInBackground(Object[] objects) {
                if (intent != null) {
                    final String action = intent.getAction();
                    Log.d(TAG, "Action: " + action);
                    if (action.equals(ACTION_UPDATE_BAKERY_WIDGETS)) {
                        handleActionUpdateBakeryWidgets();
                    }
                }

                return null;
            }
        }.execute();

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


            item = cursor.getString(itemIndex);


        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeryWidgetProvider.class));
        BakeryWidgetProvider.updateBakeryWidgets(this, appWidgetManager, appWidgetIds, item);
    }


    public static void startActionUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, BakeryWidgetService.class);
        intent.setAction(BakeryWidgetService.ACTION_UPDATE_BAKERY_WIDGETS);
        context.startService(intent);
    }


}
