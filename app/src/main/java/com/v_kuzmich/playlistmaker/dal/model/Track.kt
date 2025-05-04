package com.v_kuzmich.playlistmaker.dal.model

data class Track(
    val trackName: String,       // Название композиции
    val artistName: String,      // Имя исполнителя
    var trackTimeMillis: String, // Продолжительность трека
    val artworkUrl100: String    // Ссылка на изображение обложки
)
