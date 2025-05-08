package com.v_kuzmich.playlistmaker.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.v_kuzmich.playlistmaker.dal.model.Track

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

    fun putTrackListHistoryPreferences(trackListHistory: Array<Track>) {
        val json = Gson().toJson(trackListHistory)

        sharedPreferences.edit()
            .putString(TRACK_LIST_HISTORY_KEY, json)
            .apply()
    }

    fun getTrackListHistoryPreferences(): Array<Track> {
        val json = sharedPreferences.getString(TRACK_LIST_HISTORY_KEY, null) ?: return emptyArray<Track>()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    companion object {
        private const val PLM_PREFERENCES = "plm_preferences"
        private const val DARK_THEME_KEY = "is_dark_theme"
        private const val TRACK_LIST_HISTORY_KEY = "track_list_history"
    }
}