package com.team13.dealmymeal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class DBManagerTest: TestCase() {
    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao

    // the setUp function is called before the first test is run, it builds the database,
    // @Before
    @Before
    public override fun setUp() {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, DBManager::class.java).build()
        mealDao = db.mealDao()
    }

    // The function closeDb is called after the last test, annotated with @After
    @After
    fun closeDb() {
        db.close()
    }

    // this is a test function annotated with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadLanguage() = runBlocking {
        val meal = Meal("Spaghetti", listOf(0), 0f)
        mealDao.insert(meal)
        //val allMeals = mealDao.getAll().toList()
        val allMeals = mealDao.getAllTest()
        assertThat(allMeals.contains(meal)).isTrue()
        mealDao.deleteAll()
    }
}