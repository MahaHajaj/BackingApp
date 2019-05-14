package com.example.backingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backingapp.MainActivity;
import com.example.backingapp.R;
import com.example.backingapp.StepDetailActivity;
import com.example.backingapp.adapter.RecipeStepsAdapter;
import com.example.backingapp.model.Ingredient;
import com.example.backingapp.model.Recipe;
import com.example.backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.AdapterOnClickHandler {
    @BindView(R.id.recipe_ingerdients)
    TextView recipeIngerdients;
    @BindView(R.id.step_recycle)
    RecyclerView stepRecycle;
    ArrayList<Recipe> recipes;
    Recipe recipe;
    ArrayList<Step> steps;
    ArrayList<Ingredient> ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_frag, container, false);
        ButterKnife.bind(this, view);
        setSteps();
        if (savedInstanceState != null) {
            savedInstanceState.getSerializable("data");
        }
        return view;
    }

    public void setSteps() {
        Intent intent = getActivity().getIntent();

        recipe = (Recipe) intent.getSerializableExtra("data");
        steps = recipe.getSteps();
        ingredients = recipe.getIngredients();

        recipeIngerdients.setText("\n");
        for (int i = 0; i < ingredients.size(); i++) {
            String quantity = Float.toString(ingredients.get(i).getQuantity());
            String measure = ingredients.get(i).getMeasure();
            String ingredient = ingredients.get(i).getIngredient();
            String setIngredients = i + 1 + " - " + quantity + " " + measure + " " + ingredient;
            recipeIngerdients.append(setIngredients + "\n");
        }
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(steps, getContext(), this);
        recipeStepsAdapter.setStepsData(steps);
        stepRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        stepRecycle.setAdapter(recipeStepsAdapter);

    }

    @Override
    public void onClick(int adapterPosition) {

        if (MainActivity.getPane()) {

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle b = new Bundle();
            b.putSerializable("steps", steps.get(adapterPosition));
            b.putSerializable("stepsArraylist", steps);
            stepDetailFragment.setArguments(b);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail_tap, stepDetailFragment)
                    .commit();


        } else {


            Intent intent = new Intent(getContext(), StepDetailActivity.class);
            intent.putExtra("steps", steps.get(adapterPosition));
            intent.putExtra("stepsArraylist", steps);
            startActivity(intent);
            Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
        }
    }
}
