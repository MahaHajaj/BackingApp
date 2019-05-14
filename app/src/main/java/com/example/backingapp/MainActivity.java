package com.example.backingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.backingapp.fragment.RecipeFragment;

public class MainActivity extends AppCompatActivity {

    private static boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.recipe_frag_tap) != null) {
            mTwoPane = true;

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeFragment recipeFragment = new RecipeFragment();

            fragmentManager.beginTransaction().add(R.id.recipe_frag_tap, recipeFragment).commit();

        } else {
            mTwoPane = false;
        }
    }

    public static boolean getPane() {
        return mTwoPane;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
