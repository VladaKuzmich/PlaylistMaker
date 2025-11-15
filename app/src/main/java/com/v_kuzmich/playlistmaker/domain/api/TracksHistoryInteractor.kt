package com.v_kuzmich.playlistmaker.domain.api

import com.v_kuzmich.playlistmaker.domain.models.Track
import com.v_kuzmich.playlistmaker.presentation.adpter.TrackAdapter

interface TracksHistoryInteractor {
    fun getTrackHistoryAdapter(): TrackAdapter
    fun clearTrackHistoryList()
    fun getTrackHistoryListSize(): Int
    fun addTrackToHistory(track: Track)
}