package com.udacity.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.ui.MainActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakeryWidgetProvider extends AppWidgetProvider {

    private static final String TAG = BakeryWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String item ) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        RemoteViews rv;

        if (height < 100) {
            rv = getSingleItemRemoteView(context, item);
        } else {
            rv = getIngredientGridRemoteView(context, item);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getIngredientGridRemoteView(Context context, String item) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        if (item != null && item.length() > 0) {
            views.setTextViewText(R.id.textView_widget_grid_title, item);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
            views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);

        }
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        Intent mainIntent  = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
        views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);

        return views;
    }

    private static RemoteViews getSingleItemRemoteView(Context context, String item) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_baker);
        if (item != null && item.length() > 0) {
            views.setTextViewText(R.id.widget_single_item_textView, item);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_single_item_textView, pendingIntent);
         
        return views;
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakeryWidgetService.startActionUpdatePlantWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateBakeryWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, String item ) {

        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId, item );
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        Log.d(TAG, "height: " + height);
        Log.d(TAG, "width: " + width);
        BakeryWidgetService.startActionUpdatePlantWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

