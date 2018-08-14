package com.udacity.bakingapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.udacity.bakingapp.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import static com.udacity.bakingapp.ui.MainActivity.URL_BASE;

public class UtilClass {


    private static JSONArray bakingArray;
    private static final String MP4 = ".mp4";
    private static final String TAG = UtilClass.class.getSimpleName();

    /**
     * Reference: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     */
    public static boolean getNetworkStatus(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }


    public static Uri getVidUri(String videoUrl, String thumbNailUrl) {

        if (videoUrl.contains(MP4)) {

            return Uri.parse(videoUrl);

        } else if (thumbNailUrl.contains(MP4)) {

            return Uri.parse(thumbNailUrl);

        } else {
            Log.d(TAG, "No URL link found");
            return null;
        }

    }

    public static  JSONArray getInitialJsonArray() {
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
