package com.team13.dealmymeal

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UIRedesignTest{

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkRedesign() {

        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_save)).check(matches(hasTextColor(R.color.white)))
        onView(withId(R.id.form_edit)).check(matches(withHint("Enter the Name of your meal!")))
        onView(withId(R.id.labelName)).check(matches(withText("Meal Name")))
        onView(withId(R.id.labelCategry)).check(matches(withText("Category")))
        onView(withId(R.id.labelRating)).check(matches(withText("Rating")))
    }
}