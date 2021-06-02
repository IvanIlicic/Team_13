package com.team13.dealmymeal

import android.content.Context
import android.graphics.Color
import android.service.autofill.Validators.not
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealDao
import kotlinx.coroutines.runBlocking
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
    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao
    private val meal = Meal("Spaghetti", listOf(), 0f)
    private val meal2 = Meal("Burger", listOf(), 0f)

    @Before
    public fun setUp() = runBlocking() {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.databaseBuilder(context, DBManager::class.java, "dmm.db").build()
        mealDao = db.mealDao()
        mealDao.deleteAll()
        mealDao.insert(meal)
        mealDao.insert(meal2)
    }

    @Test
    fun search_visible() {
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
    }

    @Test
    fun execute_search() {
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withId(R.id.action_search)).perform(click()).perform(typeText(meal2.title))
        onView(withText(meal.title)).check(doesNotExist())
    }

    @Test
    fun abort_search() {
        onView(withId(R.id.navigation_overview)).perform(click())
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.action_search)).perform(pressBack())

    }

}
