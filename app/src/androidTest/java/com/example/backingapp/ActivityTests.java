package com.example.backingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ActivityTests {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkDetailStepIsVisible_RecipeDetailActivity() {

        for (int i = 0; i < 4; i++) {
            onView(withId(R.id.recipes_list)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withId(R.id.recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withId(R.id.step_recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withId(R.id.step)).check(matches(isDisplayed()));
            Espresso.pressBack();
            Espresso.pressBack();
        }
    }
}
