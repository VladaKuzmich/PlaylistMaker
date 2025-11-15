package com.v_kuzmich.playlistmaker.domain.api

import com.v_kuzmich.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>?
}