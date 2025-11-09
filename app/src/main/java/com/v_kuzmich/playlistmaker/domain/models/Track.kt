package com.v_kuzmich.playlistmaker.domain.models

data class Track(
    val trackId: Int,
    val trackName: String,          // Название композиции
    val artistName: String,         // Имя исполнителя
    val collectionName: String,     // Название альбома
    val releaseDate: String,        // Год релиза трека
    val primaryGenreName: String,   // Жанр трека
    val country: String,            // Страна исполнителя
    var trackTimeMillis: String,    // Продолжительность трека
    val artworkUrl100: String,      // Ссылка на изображение обложки
    val previewUrl: String,         // Ссылка на отрывок трека

    val year: String,
    val duration: String,
    val coverArtwork: String
)
