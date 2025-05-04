package com.v_kuzmich.playlistmaker.api

import com.v_kuzmich.playlistmaker.dal.model.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TracksResponse>
}