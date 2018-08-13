package com.udacity.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.database.BakeryContract;
import com.udacity.bakingapp.database.BakeryProvider;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplication());
    }
}


class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;
    private String item;
    private String bakingIngredient = null;


    public GridRemoteViewFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(BakeryProvider.Bakery.BAKERY,
                null,
                null,
                null,
                BakeryContract._ID);


    }

    @Override
    public void onDestroy() {
        if (mCursor != null) mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor != null)
            return mCursor.getCount();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);

        int itemIndex = mCursor.getColumnIndex(BakeryContract.ITEM);
        int ingredientIndex = mCursor.getColumnIndex(BakeryContract.INGREDIENT);
        int quantityIndex = mCursor.getColumnIndex(BakeryContract.QUANTITY);
        int unitIndex = mCursor.getColumnIndex(BakeryContract.UNIT);

        item = mCursor.getString(itemIndex);

        String ingredient = mCursor.getString(ingredientIndex);

        String quantity = mCursor.getString(quantityIndex);

        String unit = mCursor.getString(unitIndex);

        bakingIngredient = "> "+quantity + " " + unit + ", " + ingredient;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_baker_list_item);
        views.setTextViewText(R.id.widget_single_item_textView, bakingIngredient);
       // views.setImageViewResource(R.id.widget_grid_view, R.drawable.donuts);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}