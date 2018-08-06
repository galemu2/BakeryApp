package com.udacity.bakingapp.dataRecipeActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udacity.bakingapp.R;

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
    private SelectedIngredientStep mSelectedIngredientStep;

    public StepsListAdaptor(Context mContext, JSONArray mJSONArray, SelectedIngredientStep SelectedIngredientStep) {
        this.mContext = mContext;
        this.mJSONArray = mJSONArray;
        this.mSelectedIngredientStep = SelectedIngredientStep;
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
    public void onBindViewHolder(@NonNull final StepsListAdaptor.ViewHolder holder, final int position) {

        JSONObject jsonObject = null;
        String steps = null;
        String steps_long = null;
        try {
            jsonObject = mJSONArray.getJSONObject(position);
            steps = jsonObject.getString(STEPS_ID)+ ADD_SPACING;
            steps_long = jsonObject.getString(DESCRIPTION);
            if(steps.equals(PREP_STEP)){
                steps = "";
            }
            steps = steps  +jsonObject.getString(SHORT_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(steps!=null)  holder.textView_steps.setText(steps);

        if(position!=0 && steps_long!=null){
            holder.textView_steps_long.setVisibility(View.VISIBLE);
            holder.textView_steps_long.setText(steps_long);
        } else {

            holder.textView_steps_long.setVisibility(View.INVISIBLE);
        }

        if(jsonObject!=null) {

            holder.imageButton_steps_clip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedIngredientStep.onItemSelected(mContext, mJSONArray , position);

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(mJSONArray!=null)
            return mJSONArray.length();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textView_steps, textView_steps_long;
        private ImageButton imageButton_steps_clip;



        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView_steps = itemView.findViewById(R.id.textView_steps_short);
            imageButton_steps_clip = itemView.findViewById(R.id.imageButton_steps_clip);
            textView_steps_long = itemView.findViewById(R.id.textView_steps_long);
        }
    }
}
