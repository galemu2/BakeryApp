package com.udacity.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepsListAdaptor extends RecyclerView.Adapter<StepsListAdaptor.ViewHolder>{


    public static final String ADD_SPACING = ". ";
    public static final String PREP_STEP =  0 +ADD_SPACING;

    private Context mContext;
    private JSONArray mJSONArray;

    private static final String STEPS_ID = "id";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";


    public StepsListAdaptor(Context mContext, JSONArray mJSONArray) {
        this.mContext = mContext;
        this.mJSONArray = mJSONArray;
    }

    @NonNull
    @Override
    public StepsListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item_card, parent, false);
        StepsListAdaptor.ViewHolder  viewHolder = new StepsListAdaptor.ViewHolder(view);

        return viewHolder;
    }

    /*            "id":0,
            "shortDescription":"Recipe Introduction",
            "description":"Recipe Introduction",
            "videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4",
            "thumbnailURL":""
            */
    @Override
    public void onBindViewHolder(@NonNull StepsListAdaptor.ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click: "+position, Toast.LENGTH_SHORT).show();
            }
        });
        JSONObject jsonObject = null;
        String steps = null;
        try {
            jsonObject = mJSONArray.getJSONObject(position);
            steps = jsonObject.getString(STEPS_ID)+ ADD_SPACING;
            if(steps.equals(PREP_STEP)){
                steps = "";
            }
            steps = steps  +jsonObject.getString(SHORT_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.textView_steps.setText(steps);
        holder.imageButton_steps_clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos: "+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mJSONArray!=null)
            return mJSONArray.length();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textView_steps;
        private ImageButton imageButton_steps_clip;

        private TextView textView_details;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView_steps = itemView.findViewById(R.id.textView_steps_short);
            imageButton_steps_clip = itemView.findViewById(R.id.imageButton_steps_clip);
        }
    }
}
