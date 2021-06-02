package com.team13.dealmymeal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.team13.dealmymeal.data.DBManager
import java.util.*


class MainActivity : AppCompatActivity() {


    var db: DBManager? = null
    private val DATABASE_NAME = "dmmdb"

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocaleValue()
        setContentView(R.layout.main_activity)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_addMeal, R.id.navigation_dashboard, R.id.navigation_overview
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        db = Room.databaseBuilder(
                applicationContext,
                DBManager::class.java, DATABASE_NAME
        ).build()
    }


     @RequiresApi(Build.VERSION_CODES.N)
     public fun setLocale(set_locale_string: String) {

        val set_locale_list: LocaleList = LocaleList(Locale(set_locale_string))
        LocaleList.setDefault(set_locale_list)
        resources.configuration.setLocales(set_locale_list)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("pref_locale_string", set_locale_string)
        sharedPref.apply()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadLocaleValue() {
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet: String = sharedPref.getString("pref_locale_string", "")!!
        setLocale(localeToSet)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}