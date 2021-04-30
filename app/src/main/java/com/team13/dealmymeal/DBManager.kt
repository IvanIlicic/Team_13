package com.team13.dealmymeal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Meal::class], version = 1)
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
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }
        // create a buildDatabase function assign the required values
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                DBManager::class.java,
                DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

}