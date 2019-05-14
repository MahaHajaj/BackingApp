package com.example.backingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.backingapp.R;
import com.example.backingapp.RecipeDetailsActivity;
import com.example.backingapp.adapter.RecipeAdapter;
import com.example.backingapp.api.recipeApi;
import com.example.backingapp.model.Ingredient;
import com.example.backingapp.model.Recipe;
import com.example.backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeFragment extends Fragment implements RecipeAdapter.AdapterOnClickHandler {
    public static recipeApi rRecipeApi;
    private static Retrofit retrofit = null;
    @BindView(R.id.recipes_list)
    RecyclerView recipeRecyclerView;
    ArrayList<Recipe> recipeList;
    ArrayList<Recipe> recipes;
    RecipeAdapter recipeAdapter;
    Context context;

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_frag, container, false);
        ButterKnife.bind(this, view);

        getRecipes();

        return view;
    }

    private void getRecipes() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://d17h27t6h515a5.cloudfront.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            rRecipeApi = retrofit.create(recipeApi.class);

            Call<ArrayList<Recipe>> call = rRecipeApi.getRecipes();

            call.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        recipeList = response.body();


                        writeTo(recipeList);

                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    // Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void writeTo(ArrayList<Recipe> recipeList) {
        try {

            recipes = new ArrayList<Recipe>();
            for (int i = 0; i < recipeList.size(); i++) {
                String id = recipeList.get(i).getId();
                String name = recipeList.get(i).getName();
                ArrayList<Ingredient> ingredients = recipeList.get(i).getIngredients();
                ArrayList<Step> steps = recipeList.get(i).getSteps();
                int serving = recipeList.get(i).getServing();
                recipes.add(new Recipe(id, name, ingredients, steps, serving));
            }


        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

        setTo(recipes);
    }

    private void setTo(ArrayList<Recipe> recipes) {

        recipeAdapter = new RecipeAdapter(recipes, getContext(), this);
        recipeAdapter.setRecipesData(recipes);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onClick(int adapterPosition) {
        Recipe recipe = recipes.get(adapterPosition);

        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
        intent.putExtra("data", recipe);
        startActivity(intent);
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
    }
}
