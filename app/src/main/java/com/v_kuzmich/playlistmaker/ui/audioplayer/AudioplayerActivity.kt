package com.v_kuzmich.playlistmaker.ui.audioplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.v_kuzmich.playlistmaker.R
import com.v_kuzmich.playlistmaker.domain.models.Track
import com.v_kuzmich.playlistmaker.presentation.adpter.TrackAdapter
import com.v_kuzmich.playlistmaker.presentation.helper.UiHelper
import java.text.SimpleDateFormat
import java.util.Locale

class AudioplayerActivity : AppCompatActivity() {

    private lateinit var trackJson: String
    private lateinit var playTrackButton: ImageButton
    private lateinit var trackTimeTextView: TextView

    private lateinit var mainThreadHandler: Handler
    private var playerState = STATE_PLAYER_DEFAULT
    private var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        trackTimeTextView = findViewById(R.id.track_time)
        playTrackButton = findViewById(R.id.play_track_button)

        mainThreadHandler = Handler(Looper.getMainLooper())

        trackJson = intent.getStringExtra(TrackAdapter.TRACK_KEY) ?: ""
        fillTrackInfo()

        backButton.setOnClickListener {
            finish()
        }

        playTrackButton.setOnClickListener {
            playTrackButtonClick()
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

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun fillTrackInfo() {
        val track = Gson().fromJson(trackJson, Track::class.java)

        val trackName = findViewById<TextView>(R.id.track_name)
        val trackArtist = findViewById<TextView>(R.id.track_artist)
        val textYear = findViewById<TextView>(R.id.text_year)
        val textGenre = findViewById<TextView>(R.id.text_genre)
        val textCountry = findViewById<TextView>(R.id.text_country)
        val textDuration = findViewById<TextView>(R.id.text_duration)

        trackName.text = track.trackName
        trackArtist.text = track.artistName
        textYear.text = track.year
        textGenre.text = track.primaryGenreName
        textCountry.text = track.country
        textDuration.text = track.duration

        setCollectionName(track.collectionName)
        setCover(track.coverArtwork)
        preparePlayer(track.previewUrl)
    }

    private fun setCover(coverArtwork: String) {
        val imageCover = findViewById<ImageView>(R.id.image_cover)

        val uiHelper = UiHelper()

        Glide.with(this)
            .load(coverArtwork)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(uiHelper.dpToPx(8f, this)))
            .into(imageCover)
    }

    private fun setCollectionName(collectionName: String) {
        val textCollectionName = findViewById<TextView>(R.id.text_collection_name)
        val labelCollectionName = findViewById<TextView>(R.id.label_collection_name)

        if (collectionName.isEmpty()) {
            textCollectionName.visibility = View.GONE
            labelCollectionName.visibility = View.GONE
        } else {
            textCollectionName.text = collectionName
        }
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PLAYER_PREPARED
        }

        mediaPlayer.setOnCompletionListener {
            setTimerValue(0)
            mediaPlayer.seekTo(0)
            mainThreadHandler.removeCallbacks(setPlayTime())
            playTrackButton.setBackgroundResource(R.drawable.play_track_button)
            playerState = STATE_PLAYER_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()

        mainThreadHandler.post(
            setPlayTime()
        )

        playTrackButton.setBackgroundResource(R.drawable.pause_track_button)
        playerState = STATE_PLAYER_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        mainThreadHandler.removeCallbacks(setPlayTime())
        playTrackButton.setBackgroundResource(R.drawable.play_track_button)
        playerState = STATE_PLAYER_PAUSED
    }

    private fun playTrackButtonClick() {
        when(playerState) {
            STATE_PLAYER_PLAYING -> {
                pausePlayer()
            }
            STATE_PLAYER_PREPARED, STATE_PLAYER_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun setPlayTime() : Runnable {
        return object : Runnable {
            override fun run() {
                if (playerState != STATE_PLAYER_PLAYING)
                    return

                setTimerValue(mediaPlayer.currentPosition)
                mainThreadHandler.postDelayed(this, TIMER_DELAY)
            }
        }
    }

    private fun setTimerValue(msec : Int){
        trackTimeTextView.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(msec)
    }

    companion object {
        private const val TRACK_TEXT_KEY = "track_text"

        private const val TIMER_DELAY = 20L

        private const val STATE_PLAYER_DEFAULT = 0
        private const val STATE_PLAYER_PREPARED = 1
        private const val STATE_PLAYER_PLAYING = 2
        private const val STATE_PLAYER_PAUSED = 3
    }
}