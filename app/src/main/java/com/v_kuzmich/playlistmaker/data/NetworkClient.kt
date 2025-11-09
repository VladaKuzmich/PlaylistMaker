package com.v_kuzmich.playlistmaker.data

import com.v_kuzmich.playlistmaker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}