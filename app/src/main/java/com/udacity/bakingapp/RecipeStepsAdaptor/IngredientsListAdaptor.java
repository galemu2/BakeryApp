package com.udacity.bakingapp.RecipeStepsAdaptor;

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

public class IngredientsListAdaptor extends RecyclerView.Adapter<IngredientsListAdaptor.ViewHolder> {

    private static final String TAG = IngredientsListAdaptor.class.getSimpleName();
    private Context mContext;
    private JSONArray jsonArray;
    public static final String QUANTITY = "quantity";
    public static final String MEASURING = "measure";
    public static final String INGREDIENTS = "ingredient";


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

        holder.imageView.setVisibility(View.VISIBLE);
        int len = jsonArray.length();
        if (position == len) {
            holder.textView_ingredient.setVisibility(View.INVISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
            return;
        }
        try {
            jsonObject = jsonArray.getJSONObject(position);
            ingredient = jsonObject.getString(QUANTITY);
            ingredient = ingredient + " " + jsonObject.getString(MEASURING);
            ingredient =   ingredient + " , " + jsonObject.getString(INGREDIENTS); //"• "
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if (ingredient != null) {
            holder.textView_ingredient.setText(ingredient);
        }


    }

    @Override
    public int getItemCount() {
        if (jsonArray != null) {
            int len = jsonArray.length();
            if (len % 2 != 0) {

                len = len + 1;

            }
            return len;

        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_ingredient;
        private View view;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView_ingredient = itemView.findViewById(R.id.textView_ingredient);
            imageView = itemView.findViewById(R.id.imageView_ingredient_list_bullet);
        }
    }
}
