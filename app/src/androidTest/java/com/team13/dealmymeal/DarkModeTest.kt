package com.team13.dealmymeal

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4::class)
class DarkModeTest {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(Activity::class.java)

    @Before
    private fun createActivityScenarioRule(withNightMode: Boolean = false) =
        activityTestRule.scenario.apply {
            onActivity {
                AppCompatDelegate.setDefaultNightMode(
                    if (withNightMode) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

    @Test
    fun overview_clicked() {
        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_edit)).check(matches(hasBackground(R.color.edit_dark)))
    }

    /*@Test
    fun get_item_from_db() {
        onView(ViewMatchers.withText("Test1")).check(matches(isDisplayed()))
    }

    @Test
    fun category_is_displayed() {
        onView(ViewMatchers.withText("Vegan")).check(matches(isDisplayed()))
    }*/


}