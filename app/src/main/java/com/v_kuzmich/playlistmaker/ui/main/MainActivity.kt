package com.v_kuzmich.playlistmaker.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.v_kuzmich.playlistmaker.ui.media_library.MediaLibraryActivity
import com.v_kuzmich.playlistmaker.R
import com.v_kuzmich.playlistmaker.ui.search.TrackSearchActivity
import com.v_kuzmich.playlistmaker.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<Button>(R.id.settings_button)
        val searchButton = findViewById<Button>(R.id.search_button)
        val mediaLibraryButton = findViewById<Button>(R.id.media_library_button)

        searchButton.setOnClickListener {
            val intent = Intent(this, TrackSearchActivity::class.java)
            startActivity(intent)
        }

        mediaLibraryButton.setOnClickListener {
            val intent = Intent(this, MediaLibraryActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}