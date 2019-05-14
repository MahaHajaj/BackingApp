package com.example.backingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backingapp.R;
import com.example.backingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;
    private final AdapterOnClickHandler mClickHandler;

    public RecipeAdapter(ArrayList<Recipe> recipes, Context context, AdapterOnClickHandler mClickHandler) {
        this.recipes = recipes;
        this.context = context;
        this.mClickHandler = mClickHandler;
    }

    public interface AdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = R.layout.recipe_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        Recipe recipe = recipes.get(i);
        recipeViewHolder.recipeName.setText(recipe.getName());
        recipeViewHolder.serving.setText(String.valueOf(recipe.getServing()));

    }

    @Override
    public int getItemCount() {
        if (null == recipes) return 0;
        return recipes.size();
    }
    public void setRecipesData(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.serving)
        TextView serving;
        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
