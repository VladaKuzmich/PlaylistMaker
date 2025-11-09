package com.v_kuzmich.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.v_kuzmich.playlistmaker.presentation.helper.ListHistoryHelper
import com.v_kuzmich.playlistmaker.presentation.helper.PlmPreferencesHelper

class App : Application() {

    private var darkTheme = false

    val isDarkTheme: Boolean
        get() = darkTheme

    private lateinit var preferencesHelper: PlmPreferencesHelper
    lateinit var listHistoryHelper: ListHistoryHelper

    override fun onCreate() {
        super.onCreate()

        preferencesHelper = PlmPreferencesHelper(this)
        listHistoryHelper = ListHistoryHelper(preferencesHelper)

        darkTheme = preferencesHelper.getDarkThemePreferences()
        setTheme()
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        setTheme()

        preferencesHelper.putAppThemePreferences(darkTheme)
    }

    private fun setTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}