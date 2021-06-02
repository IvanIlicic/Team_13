package com.team13.dealmymeal

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealOverviewFilterCategoryTestTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_overview)).perform(ViewActions.click())
        Thread.sleep(500)
    }
    @Test
    fun filterBtnCheck()
    {
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything()).atPosition(0).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything()).atPosition(1).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.action_filter)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything()).atPosition(2).perform(ViewActions.click());
    }



}