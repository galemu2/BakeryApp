package com.udacity.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IngredientsListAdaptor extends RecyclerView.Adapter<IngredientsListAdaptor.ViewHolder> {

    public static final String BULLET = "►  ";
    private Context mContext;
    private JSONArray jsonArray;
    private static final String QUANTITY = "quantity";
    private static final String MEASUREING= "measure";
    private static final String INGREDIENTS = "ingredient";


    /*      "quantity":2,
            "measure":"CUP",
            "ingredient":"Graham Cracker crumbs"
            */

    public IngredientsListAdaptor(Context mContext, JSONArray jsonArray) {
        this.mContext = mContext;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public IngredientsListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_list, parent, false);
        IngredientsListAdaptor.ViewHolder viewHolder = new IngredientsListAdaptor.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListAdaptor.ViewHolder holder, int position) {
        JSONObject jsonObject;
        String ingredient = null;


        try {
            jsonObject = jsonArray.getJSONObject(position);
            ingredient = jsonObject.getString(QUANTITY);
            ingredient = ingredient +" "+ jsonObject.getString(MEASUREING);
            ingredient = BULLET +ingredient +" , "+ jsonObject.getString(INGREDIENTS); //"• "
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(ingredient!=null){
            holder.textView_ingredient.setText(ingredient);
        }
    }

    @Override
    public int getItemCount() {
        if (jsonArray != null)
            return jsonArray.length();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_ingredient;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView_ingredient = itemView.findViewById(R.id.textView_ingredient);
        }
    }
}
