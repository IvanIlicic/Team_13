package com.team13.dealmymeal

import android.graphics.Color
import android.service.autofill.Validators.not
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import kotlin.random.Random


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.team13.dealmymeal", appContext.packageName)
    }

    @Test
    fun test_meal_overview_list_item_edit_clicked() {
        onView(withText("2 Item")).check(matches(isDisplayed()))
        onView(withText("2 Item")).perform(click())
        //TODO should open edit popup
        onView(withText("2 Item")).check(matches(hasTextColor(R.color.red)))
    }

    @Test
    fun test_meal_overview_list_item_select_single() {
        onView(withText("3 Item")).check(matches(isDisplayed()))
        onView(withText("3 Item")).perform(longClick())
        //TODO activate selection for delete
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.teal_700)))

        onView(withText("3 Item")).check(matches(isSelected()))
    // as RecyclerView).adapter as MealOverviewAdapter).tracker?.selection?.contains("3 Item"))
    }

    @Test
    fun test_meal_overview_list_item_unselect_single() {
        // select item
        onView(withText("3 Item")).check(matches(isDisplayed()))
        onView(withText("3 Item")).perform(longClick())
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.teal_700)))

        // unselect item
        onView(withText("3 Item")).perform(click())
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.black)))
    }

    @Test
    fun test_meal_overview_list_item_select_multiple() {
        // start selection
        onView(withText("3 Item")).perform(longClick())

        // check if selection has started
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.teal_700)))
        onView(withText("3 Item")).check(matches(isSelected()))

        // select multiple items
        for (i in 4..10) {
            onView(withText("%d Item".format(i))).perform(click())
            Thread.sleep(500)
            onView(withText("%d Item".format(i))).check(matches(isSelected()))
            onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.teal_700)))
        }
    }

    @Test
    fun test_meal_overview_list_item_unselect_multiple_single() {
        // start selection
        onView(withText("3 Item")).perform(longClick())

        // check if selection has started
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.teal_700)))
        onView(withText("3 Item")).check(matches(isSelected()))

        // select multiple items
        for (i in 4..10) {
            onView(withText("%d Item".format(i))).perform(click())
            Thread.sleep(500)
            onView(withText("%d Item".format(i))).check(matches(isSelected()))
            onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.teal_700)))
        }

        // unselect random item
        var r = Random.nextInt(3, 10)
        onView(withText("%d Item".format(r))).perform(click())

        // check if all target items have been selected or unselected
        for (i in 4..10) {
            if(i == r){
                onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.black)))
            } else {
                onView(withText("%d Item".format(i))).check(matches(isSelected()))
                onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.teal_700)))
            }
        }
    }

    @Test
    fun test_meal_overview_list_item_unselect_multiple_all() {
        // start selection
        onView(withText("3 Item")).perform(longClick())

        // check if selection has started
        onView(withText("3 Item")).check(matches(hasTextColor(R.color.teal_700)))
        onView(withText("3 Item")).check(matches(isSelected()))

        // select multiple items
        for (i in 4..10) {
            onView(withText("%d Item".format(i))).perform(click())
            Thread.sleep(500)
            onView(withText("%d Item".format(i))).check(matches(isSelected()))
            onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.teal_700)))
        }

        // unselect all
        onView(withId(R.id.list)).perform(click())

        // check if all target items have been unselected
        for (i in 3..10) {
            onView(withText("%d Item".format(i))).check(matches(hasTextColor(R.color.black)))
        }
    }

    @Test
    fun delete_clicked() {
        onView(withId(R.id.delete)).perform(click())
    }

    @Test
    fun overview_clicked() {
        onView(withId(R.id.overview)).perform(click())
    }
}
