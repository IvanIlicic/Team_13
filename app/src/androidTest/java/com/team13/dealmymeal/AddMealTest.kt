package com.team13.dealmymeal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AddMealTest: TestCase() {

    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    public override fun setUp() {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.databaseBuilder(context, DBManager::class.java, "dmm.db").build()
        mealDao = db.mealDao()
    }

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
    fun saveEntryToDatabase() = runBlocking {
        val meal = Meal("asdfqwer1234", listOf() ,0f)
        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_edit))
                .perform(ViewActions.typeText(meal.title))
                .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.form_save)).perform(click())
        Thread.sleep(500)


        //val meal = Meal("Schnitzel")
        var flag = 0

        //This does not work, maybe wrong context
        val allItems = mealDao.getAllTest()

        //allItems.contains(meal)
        assertTrue(allItems.contains(meal))
        //Truth.assertThat(allItems.contains(meal)).isTrue()

        //TODO solve with delete button later
        mealDao.deleteTestItems()
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
    }

    @Test
    fun add_duplicates() = runBlocking{
        var meal = Meal("asdfqwer1234", listOf() ,0f)
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_edit))
            .perform(ViewActions.typeText(meal.title))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())

        var meal2 = Meal("asdfqwer1234", listOf() ,0f)
        Espresso.onView(ViewMatchers.withId(R.id.navigation_addMeal)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_edit))
            .perform(ViewActions.typeText(meal2.title))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.form_save)).perform(ViewActions.click())

        var count =  mealDao.getCountTitle("asdfqwer1234")
        assertEquals(1, count)

        //TODO delete Test items from database
    }

}