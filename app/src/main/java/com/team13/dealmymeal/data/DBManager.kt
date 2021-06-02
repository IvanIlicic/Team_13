package com.team13.dealmymeal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Meal::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DBManager : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object{
        private const val DB_NAME = "dmm.db"

        // Get reference of the DBManager and assign it null value
        @Volatile
        private var instance : DBManager? = null
        private val LOCK = Any()

        // create an operator fun which has context as a parameter
        // assign value to the instance variable
        operator fun invoke(context: Context, scope: CoroutineScope) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context, scope).also{
                instance = it
            }
        }
        // create a buildDatabase function assign the required values
        private fun buildDatabase(context: Context, scope: CoroutineScope) = Room.databaseBuilder(
            context.applicationContext,
            DBManager::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().addCallback(MealDatabaseCallback(scope)).build()

        private class MealDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                instance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.mealDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(mealDao: MealDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mealDao.deleteAll()

            /*Log.d("DB", "add meals")
            var word = Meal("Hello")
            mealDao.insert(word)
            word = Meal("World!")
            mealDao.insert(word)*/
        }
    }

}