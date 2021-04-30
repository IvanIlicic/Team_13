package com.team13.dealmymeal

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.ConfigurationCompat
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocaleValue()
        setContentView(R.layout.main_activity)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_addMeal, R.id.navigation_dashboard, R.id.navigation_overview))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val languageButton: Button = findViewById(R.id.changeLanguageButton)
        languageButton.setOnClickListener() {
            val languages = arrayOf("русский", "English")
            val langSelectorBuilder = AlertDialog.Builder(this)
            langSelectorBuilder.setTitle(R.string.chooseLanguage)
            langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->
                when(selection) {
                    0 -> {
                        setLocale("ru")
                    }
                    1 -> {
                        setLocale("en")
                    }

                }
                recreate()
                dialog.dismiss()
            }
            langSelectorBuilder.create().show()
        }


    }


    private fun setLocale(set_locale_string: String) {
       /* val config: Configuration = resources.configuration
        config.setLocale(Locale(locale))
        resources.updateConfiguration(config, resources.displayMetrics)

        */

        val set_locale_list: LocaleList = LocaleList(Locale(set_locale_string))
        LocaleList.setDefault(set_locale_list)
        resources.configuration.setLocales(set_locale_list)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("pref_locale_string", set_locale_string)
        sharedPref.apply()

    }


    private fun loadLocaleValue() {
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet: String = sharedPref.getString("pref_locale_string", "")!!
        setLocale(localeToSet)
    }


}