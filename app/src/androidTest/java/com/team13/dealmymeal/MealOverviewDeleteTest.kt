package com.team13.dealmymeal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4::class)
class MealOverviewDeleteTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun saveEntryToDatabase() {
        Thread.sleep(1000)
        val meal = Meal("Spaghetti", listOf(), 0f)
        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_edit))
            .perform(ViewActions.typeText(meal.title))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.form_save)).perform(click())
    }

    @Test
    fun selectSingleAndCheckDelete() {
        Thread.sleep(3000)
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withText("Spaghetti")).check(matches(isDisplayed()))
        onView(withText("Spaghetti")).perform(longClick())
        //onView(withId(R.id.action_view_delete)).check(matches(isDisplayed()))
    }

    @Test
    fun selectSingleAndCheckSelection() {
        Thread.sleep(3000)
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withText("Spaghetti")).check(matches(isDisplayed()))
        onView(withText("Spaghetti")).check(matches(hasTextColor(R.color.green)))
        //onView(withId(R.id.action_view_delete)).check(matches(isDisplayed()))
    }


}