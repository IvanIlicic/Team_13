package com.team13.dealmymeal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * from meal")
    fun getAll(): Flow<List<Meal>>

    @Insert
    fun insertAll(vararg meals: Meal)

    @Query("DELETE from meal WHERE title='asdfqwer1234'")
    fun deleteTestItems()

    @Delete
    suspend fun delete(meal: Meal)

    @Query("DELETE from meal WHERE id= :id")
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(meal: Meal)

    @Query("DELETE FROM meal")
    suspend fun deleteAll()

    @Query("UPDATE meal SET title=:title, rating=:rating, categories=:categories WHERE id=:id")
    suspend fun updateMeal(id: Long, title: String, rating: Float, categories: List<Int?>)

    //This is a test function - DO NOT USE (except in tests)
    @Query("SELECT * from meal")
    suspend fun getAllTest(): List<Meal>


    @Query("SELECT COUNT(id) FROM meal WHERE title=:title")
    suspend fun getCountTitle(title: String): Int

    @Query("DELETE from meal WHERE title=:title")
    suspend fun deleteWithTitle(title:String)


}