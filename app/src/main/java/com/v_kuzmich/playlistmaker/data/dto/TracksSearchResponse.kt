package com.v_kuzmich.playlistmaker.data.dto

class TracksSearchResponse(
    var resultCount: Int = 0,
    var results: List<TrackDto>? = null) : Response()