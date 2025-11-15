package com.v_kuzmich.playlistmaker.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.v_kuzmich.playlistmaker.domain.api.TracksHistoryRepository
import com.v_kuzmich.playlistmaker.domain.models.Track

class TracksHistoryRepositoryImpl(context: Context) : TracksHistoryRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PLM_PREFERENCES, Context.MODE_PRIVATE)

    override fun getTrackHistoryList(): Array<Track> {
        val json = sharedPreferences.getString(TRACK_LIST_HISTORY_KEY, null) ?: return emptyArray<Track>()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    override fun putTrackHistoryList(trackListHistory: Array<Track>) {
        val json = Gson().toJson(trackListHistory)

        sharedPreferences.edit()
            .putString(TRACK_LIST_HISTORY_KEY, json)
            .apply()
    }

    companion object {
        private const val PLM_PREFERENCES = "plm_preferences"
        private const val TRACK_LIST_HISTORY_KEY = "track_list_history"
    }
}