package com.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.udacity.bakingapp.utility.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.dataMainActivity.BakeryItemClicked;
import com.udacity.bakingapp.dataMainActivity.CustomBakingLoader;
import com.udacity.bakingapp.dataMainActivity.RecipeListAdaptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray>, BakeryItemClicked {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecycerView;
    private RecipeListAdaptor mAdaptor;
    private RecyclerView.LayoutManager mLayouManager;
    private Toolbar mToolbar;

    public static final String URL_BASE = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private static OkHttpClient client;

    private ImageView mBlankImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mRecycerView = findViewById(R.id.recyclerView_select_recipe);
        mBlankImageView = findViewById(R.id.blank_image);
        mBlankImageView.setVisibility(View.GONE);

        mAdaptor = new RecipeListAdaptor(this, this);

        int displayWidthPixel = getResources().getDisplayMetrics().widthPixels;
        float tabletWidth = getResources().getDimension(R.dimen.tablet_screen_width);

        if(displayWidthPixel >= (int)tabletWidth) {
            int numberOfColumns = calculateNoOfColumns(this);

            mLayouManager = new GridLayoutManager(this, numberOfColumns);
        } else {
            mLayouManager = new LinearLayoutManager(this);
        }

        mRecycerView.setLayoutManager(mLayouManager);

        mRecycerView.setAdapter(mAdaptor);



        if(Util.getNetworkStatus(this)) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad() ;
        }else {

            mBlankImageView.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Reference:
     * 1. https://square.github.io/okhttp/
     * 2. https://developers.themoviedb.org/3/movies/get-popular-movies
     */
    public static String runner(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        //OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();

        return response.body() != null ? response.body().string() : null;
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        return new CustomBakingLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {

        Log.d(TAG, "jsonArrray Size: "+data.length());
        mAdaptor.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {

         mAdaptor.setData(null);
    }


    @Override
    public void onItemSelected(View view, JSONObject jsonObject) {

        Intent intent1 = new Intent(MainActivity.this, RecipeStepsActivity.class);
        if(jsonObject!=null) {

            /** Source: https://stackoverflow.com/a/12214032/7504259
             *  date:   Aug 31 2012
                name:   marmor*/
            intent1.putExtra(RecipeStepsFragment.JSON_OBJ_BAKERY, jsonObject.toString());
        }


        //startActivity(intent);


        startActivity(intent1);
    }

    /** helper method used to calculate orientation for tablet landscape views*/
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 400;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 1;
        return noOfColumns;
    }
}
