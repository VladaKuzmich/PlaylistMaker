package com.v_kuzmich.playlistmaker.data

import com.v_kuzmich.playlistmaker.data.dto.TracksSearchRequest
import com.v_kuzmich.playlistmaker.data.dto.TracksSearchResponse
import com.v_kuzmich.playlistmaker.domain.api.TracksRepository
import com.v_kuzmich.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override fun searchTracks(expression: String): List<Track>? {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200 && (response as TracksSearchResponse).results != null) {
            return (response as TracksSearchResponse).results!!.map {
                Track(
                    trackId = it.trackId,
                    trackName = it.trackName,
                    artistName = it.artistName,
                    collectionName = it.collectionName,
                    releaseDate = it.releaseDate,
                    primaryGenreName = it.primaryGenreName,
                    country = it.country,
                    trackTimeMillis = it.trackTimeMillis,
                    artworkUrl100 = it.artworkUrl100,
                    previewUrl = it.previewUrl,

                    year = it.releaseDate.substring(0, 4),
                    duration = try {
                        SimpleDateFormat("mm:ss", Locale.getDefault()).format(it.trackTimeMillis.toInt())
                    } catch (e: Exception) {
                        "--:--"
                    },
                    coverArtwork = it.artworkUrl100.replaceAfterLast('/', COVER_TAIL)
                )
            }
        } else {
            return null
        }
    }

    companion object {
        private const val COVER_TAIL = "512x512bb.jpg"
    }

}