package com.v_kuzmich.playlistmaker.presentation.helper

import android.content.Context
import android.content.SharedPreferences

class PlmPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PLM_PREFERENCES, Context.MODE_PRIVATE)

    fun putAppThemePreferences(isDarkTheme: Boolean) {
        sharedPreferences.edit()
            .putBoolean(DARK_THEME_KEY, isDarkTheme)
            .apply()
    }

    fun getDarkThemePreferences(): Boolean {
        return sharedPreferences.getBoolean(DARK_THEME_KEY, false)
    }

    companion object {
        private const val PLM_PREFERENCES = "plm_preferences"
        private const val DARK_THEME_KEY = "is_dark_theme"
    }
}