package com.example.backingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.backingapp.fragment.RecipeStepsFragment;
import com.example.backingapp.model.Ingredient;
import com.example.backingapp.model.Recipe;
import com.example.backingapp.widget.BakingAppWidget;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;
    private boolean mTwoPane;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("data");


        mTwoPane = MainActivity.getPane();
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

        if (mTwoPane) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_recycle_tap, recipeStepsFragment)
                    .commit();

        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_detail, recipeStepsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if ((sharedPreferences.getInt(BakingAppWidget.ID, -1) == Integer.parseInt(recipe.getId()))) {
            menu.findItem(R.id.add).setTitle("Remove From Widget");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.add) {
            if (sharedPreferences.getInt(BakingAppWidget.ID, -1) == Integer.parseInt(recipe.getId())) {
                sharedPreferences.edit()
                        .remove(BakingAppWidget.ID)
                        .remove(BakingAppWidget.RECIPE_TITLE)
                        .remove(BakingAppWidget.RECIPE_TEXT)
                        .apply();

                item.setTitle("Add To Widget");

                Toast.makeText(getBaseContext(), "recipe removed", Toast.LENGTH_SHORT).show();
            } else {
                sharedPreferences.edit()
                        .putInt(BakingAppWidget.ID, Integer.parseInt(recipe.getId()))
                        .putString(BakingAppWidget.RECIPE_TITLE, recipe.getName())
                        .putString(BakingAppWidget.RECIPE_TEXT, setIngredients())
                        .apply();

                item.setTitle("Remove From Widget");

                Toast.makeText(getBaseContext(), "recipe added", Toast.LENGTH_SHORT).show();
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getBaseContext(), BakingAppWidget.class));
            BakingAppWidget bakingAppWidget = new BakingAppWidget();
            bakingAppWidget.onUpdate(this, appWidgetManager, appWidgetIds);
        }

        return super.onOptionsItemSelected(item);
    }

    private String setIngredients() {
        int count = 1;
        String setIntegernt = " ";
        for (Ingredient ingredient : recipe.getIngredients()) {
            setIntegernt += count + " - " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient() + "\n";
            count++;
        }
        return setIntegernt;
    }
}
