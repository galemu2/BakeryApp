package com.udacity.bakingapp.ui;


import android.os.SystemClock;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public static final String TEXT = "Recipe Introduction";

    @Before
    public void beforeRecipeStepsActivityTest(){

        /** source: https://stackoverflow.com/a/32788964/7504259
         *  Date:   Sep 25 '15 at 19:01
         *  Name:   Andrey*/
        onView(withId(R.id.recyclerView_select_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
    @Test
    public void recipeStepsActivityTest() {


        /** Source: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
         *  By:     Romain Piel
         *  Date:   Jul 18, 2015*/
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withId(R.id.textView_video_steps))
                .check(matches(withText(TEXT)));


    }


}
