package com.v_kuzmich.playlistmaker.dal.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v_kuzmich.playlistmaker.dal.model.Track

class TrackAdapter  (
    private val data: List<Track>
) : RecyclerView.Adapter<TrackViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}