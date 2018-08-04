package com.udacity.bakingapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;


public class VideoDetailFragment extends Fragment {

    private static final String TAG = VideoDetailFragment.class.getSimpleName();
    private static final String TEST_URL_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4";
    public static final String MP4 = ".mp4";

    private SimpleExoPlayerView simpleExoPlayerView;
    public static SimpleExoPlayer mSimplePlayer;

    private TextView textView_video_detail;

    public static final String PASSED_JSON_OBJ = "padded-data from recipe fragment";
    private static final String RECIPE_STEP_ID = "id";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VID_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";

    private String video_url, thumbNail_url;
    private String long_discription;
    private JSONObject videoJSONObj;
    Uri testUri = null;
    public VideoDetailFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_detail, container, false);
        //super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String s = bundle.getString(PASSED_JSON_OBJ);
            try {
                videoJSONObj = new JSONObject(s);

                video_url = videoJSONObj.getString(VID_URL);
                thumbNail_url = videoJSONObj.getString(THUMBNAIL_URL);
                long_discription = videoJSONObj.getString(DESCRIPTION);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "saved bundle is  null");
        }
        textView_video_detail = rootView.findViewById(R.id.textView_video_steps);
        textView_video_detail.setText(long_discription);

        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_error_outline_white_48dp));



        if (video_url.contains(MP4)) {
              testUri = Uri.parse(video_url);
              Log.d(TAG, "VIDEO Url contains link");
        } else if(thumbNail_url.contains(MP4)){
            testUri = Uri.parse(thumbNail_url);
            Log.d(TAG, "ThumbNail Url contains link");
        } else {
            Log.d(TAG, "No URL link found");
        }



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (testUri != null) {
            initializePlayer(testUri);
        } else {
            initializePlayer(testUri);

        }
    }

    private void initializePlayer(Uri videoUri) {
        if (mSimplePlayer == null) {

            //1 create a default trackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            //2 Create the player
            mSimplePlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            //bind the player to a view

            simpleExoPlayerView.setPlayer(mSimplePlayer);
            mSimplePlayer.setPlayWhenReady(true);


            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "bakingapp"), null);
            MediaSource videoSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

            mSimplePlayer.prepare(videoSource);

        }
    }

    /* release the exoPlayer*/
    public     void releasePlayer() {
        if(mSimplePlayer!=null) {
            mSimplePlayer.stop();
            mSimplePlayer.release();
            mSimplePlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
