package com.team13.dealmymeal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.MealDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealOverviewFilterRatingTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao

    @Before
    public fun setUp() = runBlocking() {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.databaseBuilder(context, DBManager::class.java, "dmm.db").build()
        mealDao = db.mealDao()
        mealDao.deleteAll()
    }


    @Test
    fun filterRatingCheck()  = runBlocking {
        onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.form_edit))
            .perform(ViewActions.typeText("Nudeln"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.form_ratingBar)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.navigation_overview)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.action_filter_star)).perform(ViewActions.click());
        Espresso.onData(CoreMatchers.anything()).atPosition(2).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.action_filter_star)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText("Nudeln")).check(matches(isDisplayed()))
        mealDao.deleteAll()
    }


    @Test
    fun filterResetRatingCheck()  = runBlocking {
        onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.form_edit))
            .perform(ViewActions.typeText("Nudeln"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.form_ratingBar)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.navigation_overview)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.action_filter_star)).perform(ViewActions.click());
        Espresso.onData(CoreMatchers.anything()).atPosition(2).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.action_filter_star)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText("Nudeln")).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.action_filter_star)).perform(ViewActions.doubleClick());
        Espresso.onData(CoreMatchers.anything()).atPosition(1).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.action_filter_star)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.action_filter_star)).perform(ViewActions.doubleClick());
        Espresso.onData(CoreMatchers.anything()).atPosition(2).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.action_filter_star)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText("Nudeln")).check(matches(isDisplayed()))
        mealDao.deleteAll()
    }


}