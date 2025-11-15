package com.v_kuzmich.playlistmaker.domain.api

import com.v_kuzmich.playlistmaker.domain.models.Track

interface TracksHistoryRepository {
    fun getTrackHistoryList(): Array<Track>
    fun putTrackHistoryList(trackListHistory: Array<Track>)
}