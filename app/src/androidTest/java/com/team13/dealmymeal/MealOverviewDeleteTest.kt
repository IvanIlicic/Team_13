package com.team13.dealmymeal

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealDao
import com.team13.dealmymeal.ui.overview.MealOverviewAdapter
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4::class)
class MealOverviewDeleteTest: TestCase() {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao
    private val meal = Meal("Spaghetti", listOf(), 0f)

    override fun setUp() = runBlocking {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.databaseBuilder(context, DBManager::class.java, "dmm.db").build()
        mealDao = db.mealDao()
    }

    @Test
    fun selectSingleAndCheckDelete() {
        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_edit))
            .perform(ViewActions.typeText(meal.title))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.form_save)).perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.navigation_overview)).perform(click())

        var pos = 0
        activityRule.scenario.onActivity {
            pos = (it.findViewById<RecyclerView>(R.id.list).adapter as MealOverviewAdapter).currentList.indexOf(meal)
        }
        onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition<MealOverviewAdapter.ViewHolder>(pos))
        Thread.sleep(500)

        onView(withText("Spaghetti")).check(matches(isDisplayed()))
        onView(withText("Spaghetti")).perform(longClick())

        Thread.sleep(1000)
    }

    override fun tearDown() = runBlocking {
        mealDao.delete(meal)
    }
}