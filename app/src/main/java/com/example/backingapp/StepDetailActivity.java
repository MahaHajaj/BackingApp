package com.example.backingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.backingapp.fragment.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_step_detail, stepDetailFragment)
                .commit();

    }
}
