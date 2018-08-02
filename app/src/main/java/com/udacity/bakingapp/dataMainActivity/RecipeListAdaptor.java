package com.udacity.bakingapp.dataMainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeListAdaptor extends RecyclerView.Adapter<RecipeListAdaptor.ViewHolder> {

    private Context mContext;
    private static JSONArray bakingArray;
    private static final String RECIPE_NAME = "name";
    private static final String SERVING_SIZE = "servings";
    private BakeryItemClicked bakeryItemClicked;

    public RecipeListAdaptor(Context context, BakeryItemClicked bakeryItemClicked) {
        this.mContext = context;
        this.bakeryItemClicked = bakeryItemClicked;

    }


    @NonNull
    @Override
    public RecipeListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdaptor.ViewHolder viewHolder, final int position) {
        JSONObject jsonObject =null;
        String recipeName = null;
        String servings = null;
        try {
            jsonObject = (JSONObject) bakingArray.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject!=null){

            try {
                recipeName = jsonObject.getString(RECIPE_NAME);
                servings = jsonObject.getString(SERVING_SIZE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(recipeName!=null){
                viewHolder.textViewName.setText(recipeName);
            }

            if(servings !=null){
                servings = "Serving size: "+servings;
                viewHolder.textViewServingSize.setText(servings);
            }
        }


        final JSONObject finalJsonObject = jsonObject;
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bakeryItemClicked.onItemSelected(v, finalJsonObject);
             }
        });

    }


    @Override
    public int getItemCount() {
        if (bakingArray != null)
            return bakingArray.length();
        return 0;
    }


    public  void setData(JSONArray jsonArray){
         bakingArray = jsonArray;
         notifyDataSetChanged();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView textViewName, textViewServingSize;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            mImageView = itemView.findViewById(R.id.imageView_recipe_list);
            textViewName = itemView.findViewById(R.id.textView_recipe_name);
            textViewServingSize = itemView.findViewById(R.id.textView_serving_size);
        }
    }
}
