package com.v_kuzmich.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.v_kuzmich.playlistmaker.dal.adapter.TrackAdapter
import com.v_kuzmich.playlistmaker.dal.model.Track

class SearchActivity : AppCompatActivity() {

    private var searchText: String = SEARCH_TEXT_DEFAULT
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        val clearButton = findViewById<ImageView>(R.id.clear_search)

        searchEditText = findViewById(R.id.search_edit_text)

        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            searchEditText.setText("")

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        searchEditText.addTextChangedListener(textWatcher)

        fillTrackList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT, SEARCH_TEXT_DEFAULT)

        searchEditText.setText(searchText)
    }

    private fun fillTrackList() {
        val trackAdapter = TrackAdapter(
            listOf(
                Track(getString(R.string.track_name_1), getString(R.string.artist_name_1), getString(R.string.track_time_1), getString(R.string.track_artwork_1)),
                Track(getString(R.string.track_name_2), getString(R.string.artist_name_2), getString(R.string.track_time_2), getString(R.string.track_artwork_2)),
                Track(getString(R.string.track_name_3), getString(R.string.artist_name_3), getString(R.string.track_time_3), getString(R.string.track_artwork_3)),
                Track(getString(R.string.track_name_4), getString(R.string.artist_name_4), getString(R.string.track_time_4), getString(R.string.track_artwork_4)),
                Track(getString(R.string.track_name_5), getString(R.string.artist_name_5), getString(R.string.track_time_5), getString(R.string.track_artwork_5)),
            )
        )

        val trackList = findViewById<RecyclerView>(R.id.track_list)
        trackList.adapter = trackAdapter
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val SEARCH_TEXT_DEFAULT = ""
    }
}