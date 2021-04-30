package com.team13.dealmymeal

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTestDMM002 {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun overview_clicked() {
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fragment_addMeal)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)
    }

    @Test
    fun form_buttonSaveTest() {
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())

    }


    @Test
    fun form_checkBoxes() {
        Espresso.onView(ViewMatchers.withId(R.id.check_meat)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_meat)).check(ViewAssertions.matches(ViewMatchers.isChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.check_veggie)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_veggie)).check(ViewAssertions.matches(ViewMatchers.isChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.check_special)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_special)).check(ViewAssertions.matches(ViewMatchers.isChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.check_meat)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_meat)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.check_veggie)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_veggie)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.check_special)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.check_special)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()))

    }

    @Test
    fun form_TextInput() {
        Espresso.onView(ViewMatchers.withId(R.id.form_edit)).perform(ViewActions.typeText("test"))
        Espresso.onView(ViewMatchers.withId(R.id.form_edit)).check(ViewAssertions.matches(ViewMatchers.withText("test")))

    }

    @Test
    fun form_ratingStars() {
        Espresso.onView(ViewMatchers.withId(R.id.form_ratingBar)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_ratingBar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun form_inputDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.form_edit)).perform(ViewActions.typeText("test"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_showEntry)).check(ViewAssertions.matches(ViewMatchers.withText("test")))

    }


}