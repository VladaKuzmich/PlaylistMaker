package com.v_kuzmich.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.v_kuzmich.playlistmaker.helper.ListHistoryHelper
import com.v_kuzmich.playlistmaker.helper.PlmPreferencesHelper

class App : Application() {

    var isDarkTheme = false

    private lateinit var preferencesHelper: PlmPreferencesHelper
    lateinit var listHistoryHelper: ListHistoryHelper

    override fun onCreate() {
        super.onCreate()

        preferencesHelper = PlmPreferencesHelper(this)
        listHistoryHelper = ListHistoryHelper(preferencesHelper)

        isDarkTheme = preferencesHelper.getDarkThemePreferences()
        setTheme()
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        isDarkTheme = darkThemeEnabled
        setTheme()

        preferencesHelper.putAppThemePreferences(isDarkTheme)
    }

    private fun setTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}