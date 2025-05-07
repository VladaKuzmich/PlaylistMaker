package com.v_kuzmich.playlistmaker.dal.adapter

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.v_kuzmich.playlistmaker.App
import com.v_kuzmich.playlistmaker.AudioplayerActivity
import com.v_kuzmich.playlistmaker.dal.model.Track

class TrackAdapter() : RecyclerView.Adapter<TrackViewHolder> () {

    var tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            (holder.itemView.context.applicationContext as App).listHistoryHelper.addTrackToHistory(track)

            val intent = Intent(holder.itemView.context, AudioplayerActivity::class.java)
            intent.putExtra(TRACK_KEY, Gson().toJson(track))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    companion object {
        const val TRACK_KEY = "track"
    }
}