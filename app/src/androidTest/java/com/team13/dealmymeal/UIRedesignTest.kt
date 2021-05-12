package com.team13.dealmymeal

import android.widget.Button
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Null

@RunWith(AndroidJUnit4::class)
class UIRedesignTest{

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkRedesign() {

        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_save)).check(matches(hasTextColor(R.color.white)))
        onView(withId(R.id.form_edit)).check(matches(withHint("Enter the Name of your meal!")))
        onView(withId(R.id.textView)).check(matches(withText("Meal Name")))
        onView(withId(R.id.textView3)).check(matches(withText("Category")))
        onView(withId(R.id.textView4)).check(matches(withText("Rating")))
    }
}