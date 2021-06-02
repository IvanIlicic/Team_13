package com.team13.dealmymeal

import android.app.Application
import com.team13.dealmymeal.data.DBManager
import com.team13.dealmymeal.data.MealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MealApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { DBManager.invoke(this, applicationScope) }
    val repository by lazy { MealRepository(database.mealDao()) }
}