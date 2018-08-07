package com.udacity.bakingapp.ui;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.utility.UtilClass;

import org.json.JSONException;
import org.json.JSONObject;

import static com.udacity.bakingapp.utility.UtilClass.getVidUri;


public class VideoRecipeFragment extends Fragment  {

    private static final String TAG = VideoRecipeFragment.class.getSimpleName();

    private static final String TEST_URL_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4";

    public static final String PASSED_JSON_OBJ = "json_obj_for current_position";


    public static final String PASSED_DESCRIPTION_MULTI_PAIN_VIEW = "long description in multi pain view";
    public static final String PASSED_VIDEO_URL_MULTI_PAIN_VIEW = "string value for url in multi pain view";
    public static final String CHECK_NETWORK_STATUS = "Check network status";

    private SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer mSimplePlayer;

    private TextView textView_video_detail;

    private static final String DESCRIPTION = "description";
    private static final String VID_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";

    private String video_url, thumbNail_url;
    private String long_description;

    private JSONObject videoJSONObj;
    Uri testUri = null;
    MediaSource videoSource = null;

    public VideoRecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_detail, container, false);
        //super.onCreateView(inflater, container, savedInstanceState);
        textView_video_detail = rootView.findViewById(R.id.textView_video_steps);
        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.requestFocus();
        //add background image for blank view

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),
                R.drawable.baseline_cloud_off_white_48));

        Bundle bundle = getArguments();

        if(!RecipeStepsActivity.mTwoPain) {


            if (bundle != null) {

                String s = bundle.getString(PASSED_JSON_OBJ);

                try {
                    videoJSONObj = new JSONObject(s);

                    video_url = videoJSONObj.getString(VID_URL);
                    thumbNail_url = videoJSONObj.getString(THUMBNAIL_URL);
                    long_description = videoJSONObj.getString(DESCRIPTION);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "saved bundle is  null");
            }

            testUri = getVidUri(video_url, thumbNail_url);

            /** Source: https://stackoverflow.com/a/2799001/7504259
             *  Date:   May 9 2010
             Name:   hackbod*/
            int currentOrientation = getResources().getConfiguration().orientation;
            if(currentOrientation== Configuration.ORIENTATION_PORTRAIT) {


                textView_video_detail.clearComposingText();
                textView_video_detail.setText(long_description);
            }
        } else {

            if(bundle!=null){
                String videoUrl = bundle.getString(PASSED_VIDEO_URL_MULTI_PAIN_VIEW);
                if(videoUrl!=null  ){
                    testUri = Uri.parse(videoUrl);

                }
                long_description = bundle.getString(PASSED_DESCRIPTION_MULTI_PAIN_VIEW);
            }

            textView_video_detail.clearComposingText();
            textView_video_detail.setText(long_description);
        }





        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();

        if(UtilClass.getNetworkStatus(getContext()) ) initializePlayer(testUri);
        else Toast.makeText(getContext(), CHECK_NETWORK_STATUS, Toast.LENGTH_SHORT).show();
    }

    private void initializePlayer(Uri videoUri) {
        if (mSimplePlayer == null) {

            //1 create a default trackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            //2 Create the player
            mSimplePlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            //bind the player to a view
            simpleExoPlayerView.setPlayer(mSimplePlayer);

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "bakingapp"), null);

            videoSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

            mSimplePlayer.prepare(videoSource);
            mSimplePlayer.setPlayWhenReady(true);


        } else {

            if (videoSource != null) {
                videoSource.releaseSource();
            }
            if (mSimplePlayer != null) {
                releasePlayer();
            }

            if(UtilClass.getNetworkStatus(getContext())) initializePlayer(testUri);
            else Toast.makeText(getContext(), CHECK_NETWORK_STATUS, Toast.LENGTH_SHORT).show();
        }

        if(videoUri==null){
            Toast.makeText(getContext(), "No video available", Toast.LENGTH_SHORT).show();
        }
    }

    /* release the exoPlayer*/
    public void releasePlayer() {

        mSimplePlayer.stop();
        mSimplePlayer.release();
        mSimplePlayer = null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSimplePlayer != null) releasePlayer();
    }

}
