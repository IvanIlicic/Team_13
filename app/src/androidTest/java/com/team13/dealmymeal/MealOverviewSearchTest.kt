package com.team13.dealmymeal

import android.graphics.Color
import android.service.autofill.Validators.not
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.team13.dealmymeal.RecyclerViewMatcher.Companion.populate
import org.junit.Assert.assertEquals
import org.junit.Before
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
class MealOverviewSearchTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    @Test
    fun switch_overview() {
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        onView(withId(R.id.list)).perform(populate(15))
        Thread.sleep(500)
    }

    @Test
    fun search_visible() {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
    }

    @Test
    fun execute_search() {
        val r = Random.nextInt(6, 10)
        onView(withId(R.id.action_search)).perform(click()).perform(typeText("%d".format(r)))

        for (i in 1..15) {
            if (i != r) {
                onView(withText("%d Item".format(i))).check(doesNotExist())
            } else {
                onView(withText("%d Item".format(i))).check(matches(isDisplayed()))
            }
        }
    }

    @Test
    fun abort_search() {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.action_search)).perform(pressBack())

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.team13.dealmymeal", appContext.packageName)
    }
}
