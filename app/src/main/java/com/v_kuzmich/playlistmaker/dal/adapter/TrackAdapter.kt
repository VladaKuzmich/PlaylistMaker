package com.v_kuzmich.playlistmaker.dal.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v_kuzmich.playlistmaker.App
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
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}