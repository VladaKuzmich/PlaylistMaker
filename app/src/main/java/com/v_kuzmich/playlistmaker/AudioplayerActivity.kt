package com.v_kuzmich.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.v_kuzmich.playlistmaker.dal.adapter.TrackAdapter
import com.v_kuzmich.playlistmaker.dal.model.Track
import com.v_kuzmich.playlistmaker.helper.UiHelper
import java.text.SimpleDateFormat
import java.util.Locale

class AudioplayerActivity : AppCompatActivity() {

    private lateinit var trackJson: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        trackJson = intent.getStringExtra(TrackAdapter.TRACK_KEY) ?: ""
        fillTrackInfo()

        val backButton = findViewById<ImageButton>(R.id.back_button)

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(TRACK_TEXT_KEY, trackJson)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        trackJson = savedInstanceState.getString(TRACK_TEXT_KEY, "")

        fillTrackInfo()
    }

    private fun fillTrackInfo() {
        val track = Gson().fromJson(trackJson, Track::class.java)

        val trackName = findViewById<TextView>(R.id.track_name)
        val trackArtist = findViewById<TextView>(R.id.track_artist)

        val textDuration = findViewById<TextView>(R.id.text_duration)
        val textCollectionName = findViewById<TextView>(R.id.text_collection_name)
        val labelCollectionName = findViewById<TextView>(R.id.label_collection_name)
        val textYear = findViewById<TextView>(R.id.text_year)
        val textGenre = findViewById<TextView>(R.id.text_genre)
        val textCountry = findViewById<TextView>(R.id.text_country)

        val imageCover = findViewById<ImageView>(R.id.image_cover)

        trackName.text = track.trackName
        trackArtist.text = track.artistName
        textDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt())

        if (track.collectionName.isEmpty()) {
            textCollectionName.visibility = View.GONE
            labelCollectionName.visibility = View.GONE
        } else {
            textCollectionName.text = track.collectionName
        }

        textYear.text = track.releaseDate.substring(0, 4)
        textGenre.text = track.primaryGenreName
        textCountry.text = track.country

        val uiHelper = UiHelper()

        Glide.with(this)
            .load(getCoverArtwork(track.artworkUrl100))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(uiHelper.dpToPx(8f, this)))
            .into(imageCover)
    }

    private fun getCoverArtwork(artworkUrl100: String) = artworkUrl100.replaceAfterLast('/',COVER_TAIL)

    companion object {
        private const val TRACK_TEXT_KEY = "track_text"
        private const val COVER_TAIL = "512x512bb.jpg"
    }
}