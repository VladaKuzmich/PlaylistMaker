package com.v_kuzmich.playlistmaker.helper

import com.v_kuzmich.playlistmaker.dal.adapter.TrackAdapter
import com.v_kuzmich.playlistmaker.dal.model.Track

const val TRACK_LIST_HISTORY_MAX_ITEM = 10

class ListHistoryHelper(private val preferencesHelper: PlmPreferencesHelper) {

    private val tracksHistory = ArrayList<Track>()
    val adapterHistory = TrackAdapter()

    init {
        tracksHistory.addAll(preferencesHelper.getTrackListHistoryPreferences())
        adapterHistory.tracks = tracksHistory
    }

    fun addTrackToHistory(track: Track) {
        val historyList = preferencesHelper.getTrackListHistoryPreferences().filter { it.trackId != track.trackId }
        tracksHistory.clear()
        tracksHistory.add(0, track)
        tracksHistory.addAll(historyList.take(TRACK_LIST_HISTORY_MAX_ITEM - 1))
        adapterHistory.notifyDataSetChanged()
        preferencesHelper.putTrackListHistoryPreferences(tracksHistory.toTypedArray())
    }

    fun clearTrackHistoryList() {
        tracksHistory.clear()
        adapterHistory.notifyDataSetChanged()
        preferencesHelper.putTrackListHistoryPreferences(tracksHistory.toTypedArray())
    }

    fun getTrackHistorySize() = tracksHistory.size
}