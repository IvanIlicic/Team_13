package com.team13.dealmymeal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealDao {
    @Query("SELECT * from meal")
    fun getAll(): List<Meal>

    @Insert
    fun insertAll(vararg meals: Meal)

    @Delete
    fun delete(meal: Meal)
}