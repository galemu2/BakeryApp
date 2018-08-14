package com.udacity.bakingapp.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.udacity.bakingapp.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String INGREDIENTS_TITLE = "Ingredients";
    private static final String STEPS_TITLE = "Steps";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {

        /** source: https://stackoverflow.com/a/32788964/7504259
         *  Date:   Sep 25 '15 at 19:01
         *  Name:   Andrey*/
        onView( withId(R.id.recyclerView_select_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.textView_ingredients_title)).check(matches(withText(INGREDIENTS_TITLE)));
    }

    @Test
    public void mainActivityTest2() {

        /** source: https://stackoverflow.com/a/32788964/7504259
         *  Date:   Sep 25 '15 at 19:01
         *  Name:   Andrey*/
        onView( withId(R.id.recyclerView_select_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.textView_steps_title)).check(matches(withText(STEPS_TITLE)));
    }
}
