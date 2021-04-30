package com.team13.dealmymeal

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun buttonExists()
    {
      onView(withId(R.id.btnmenu)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun buttonClickable()
    {
        onView(withId(R.id.btnmenu)).perform(click())
    }

    @Test
    fun menuOpen()
    {
        onView(withId(R.id.btnmenu)).perform(click())
        onView(withId(R.id.nav_bar_id)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun openViewBar()
    {
        onView(withId(R.id.btnmenu)).perform(click())
        onView(withId(R.id.nav_bar_id)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun closeViewBar()
    {
        onView(withId(R.id.btnmenu)).perform(click())
        onView(withId(R.id.btnmenu)).perform(click())
        onView(withId(R.id.nav_bar_id)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
    }


}