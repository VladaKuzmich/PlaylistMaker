package com.v_kuzmich.playlistmaker.ui.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.v_kuzmich.playlistmaker.App
import com.v_kuzmich.playlistmaker.Creator.provideTracksInteractor
import com.v_kuzmich.playlistmaker.R
import com.v_kuzmich.playlistmaker.domain.api.TracksInteractor
import com.v_kuzmich.playlistmaker.domain.impl.TracksInteractorImpl
import com.v_kuzmich.playlistmaker.domain.models.Track
import com.v_kuzmich.playlistmaker.presentation.adpter.TrackAdapter

class TrackSearchActivity : AppCompatActivity() {

    private var searchText: String = SEARCH_TEXT_DEFAULT

    private lateinit var searchEditText: EditText
    private lateinit var notFoundedErrorLayout: LinearLayout
    private lateinit var networkErrorLayout: LinearLayout
    private lateinit var trackListHistoryLayout: LinearLayout
    private lateinit var trackList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var handler: Handler

    private lateinit var tracksInteractor: TracksInteractorImpl

    private val tracks = ArrayList<Track>()
    private val adapter = TrackAdapter()

    private val searchRunnable = Runnable { doSearch() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        tracksInteractor = provideTracksInteractor()

        val backButton = findViewById<ImageButton>(R.id.back_button)
        val clearButton = findViewById<ImageView>(R.id.clear_search)
        val trackListHistory = findViewById<RecyclerView>(R.id.track_list_history)
        val refreshButton = findViewById<Button>(R.id.refresh_button)
        val clearHistoryButton = findViewById<Button>(R.id.clear_history_button)

        searchEditText = findViewById(R.id.search_edit_text)
        notFoundedErrorLayout = findViewById(R.id.not_founded_error_layout)
        networkErrorLayout = findViewById(R.id.network_error_layout)
        trackListHistoryLayout = findViewById(R.id.track_list_history_layout)
        trackList = findViewById(R.id.track_list)
        progressBar = findViewById(R.id.progress_bar)

        handler = Handler(Looper.getMainLooper())

        adapter.tracks = tracks
        trackList.adapter = adapter

        val app = (applicationContext as? App) ?: return
        trackListHistory.adapter = app.listHistoryHelper.adapterHistory

        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            searchEditText.setText("")

            clearSearchList()

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }

        refreshButton.setOnClickListener {
            doSearch()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                clearButton.visibility = clearButtonVisibility(s)
                setHistoryVisibility(searchEditText.hasFocus() && s?.isEmpty() == true)
                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        searchEditText.addTextChangedListener(textWatcher)

        searchEditText.setOnFocusChangeListener { _, hasFocus ->
            setHistoryVisibility(hasFocus && searchEditText.text.isEmpty())
        }

        clearHistoryButton.setOnClickListener {
            app.listHistoryHelper.clearTrackHistoryList()
            setHistoryVisibility(false)
        }
    }

    private fun setHistoryVisibility(visible: Boolean) {
        val app = (applicationContext as? App) ?: return
        val trackHistoryListSize = app.listHistoryHelper.getTrackHistorySize()

        if (visible && trackHistoryListSize > 0) {
            setNetworkErrorLayoutVisible(false)
            setNotFoundedErrorLayoutVisible(false)

            trackList.visibility = View.GONE
            trackListHistoryLayout.visibility = View.VISIBLE
        } else {
            trackList.visibility = View.VISIBLE
            trackListHistoryLayout.visibility = View.GONE
        }
    }

    private fun setNetworkErrorLayoutVisible(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        if (networkErrorLayout.visibility != visibility)
            networkErrorLayout.visibility = visibility
    }

    private fun setNotFoundedErrorLayoutVisible(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        if (notFoundedErrorLayout.visibility != visibility)
            notFoundedErrorLayout.visibility = visibility
    }

    private fun doSearch() {

        progressBar.visibility = View.VISIBLE
        trackList.visibility = View.GONE

        //при инициации поиска скрываются сообщения об ошибках
        setNetworkErrorLayoutVisible(false)
        setNotFoundedErrorLayoutVisible(false)

        tracksInteractor.searchTracks(searchEditText.text.toString(), object : TracksInteractor.TracksConsumer{

            override fun consume(foundTracks: List<Track>?) {
                if (foundTracks == null) {
                    showNetworkError()
                    return
                }

                if (foundTracks.isEmpty()) {
                    showNotFoundedError()
                }
                else {
                    tracks.clear()
                    tracks.addAll(foundTracks)
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }

                runOnUiThread {
                    trackList.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }

            private fun showNetworkError() {
                clearSearchList()
                setNotFoundedErrorLayoutVisible(false)
                setNetworkErrorLayoutVisible(true)
            }

            private fun showNotFoundedError() {
                clearSearchList()
                setNetworkErrorLayoutVisible(false)
                setNotFoundedErrorLayoutVisible(true)
            }
        }
        )

    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
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

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun clearSearchList() {
        tracks.clear()
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val SEARCH_TEXT_DEFAULT = ""
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}