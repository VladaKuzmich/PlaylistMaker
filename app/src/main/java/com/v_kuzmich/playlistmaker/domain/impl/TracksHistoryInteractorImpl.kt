package com.v_kuzmich.playlistmaker.domain.impl

import com.v_kuzmich.playlistmaker.domain.api.TracksHistoryInteractor
import com.v_kuzmich.playlistmaker.domain.api.TracksHistoryRepository
import com.v_kuzmich.playlistmaker.domain.models.Track
import com.v_kuzmich.playlistmaker.presentation.adpter.TrackAdapter

class TracksHistoryInteractorImpl(private val repository: TracksHistoryRepository) : TracksHistoryInteractor {

    private val tracksHistory = ArrayList<Track>()
    private val adapterHistory = TrackAdapter()

    init {
        tracksHistory.addAll(repository.getTrackHistoryList())
        adapterHistory.tracks = tracksHistory
    }

    override fun getTrackHistoryAdapter(): TrackAdapter  = adapterHistory

    override fun getTrackHistoryListSize(): Int = tracksHistory.size

    override fun addTrackToHistory(track: Track) {
        val historyList = repository.getTrackHistoryList().filter { it.trackId != track.trackId }
        tracksHistory.clear()
        tracksHistory.add(0, track)
        tracksHistory.addAll(historyList.take(TRACK_LIST_HISTORY_MAX_ITEM - 1))

        repository.putTrackHistoryList(tracksHistory.toTypedArray())
    }

    override fun clearTrackHistoryList() {
        tracksHistory.clear()

        repository.putTrackHistoryList(tracksHistory.toTypedArray())
    }

    companion object {
        private const val TRACK_LIST_HISTORY_MAX_ITEM = 10
    }
}