package com.team13.dealmymeal

import android.widget.Button
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.anything
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun overview_clicked() {
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fragment_addMeal)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)
    }

    @Test
    fun changeLanguageButtonClickable()
    {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.changeLanguageButton)).perform(click())
    }

    @Test
    fun languageAlertDialog()
    {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.changeLanguageButton)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.changeLanguageButton)).perform(click());
        onData(anything()).atPosition(0).perform(click());
    }

    @Test
    fun languageCheck()
    {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.changeLanguageButton)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(withText("save")).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.changeLanguageButton)).perform(click())
        onData(anything()).atPosition(0).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(withText("спасти")).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.changeLanguageButton)).perform(click())
        onData(anything()).atPosition(1).perform(click())
    }
}