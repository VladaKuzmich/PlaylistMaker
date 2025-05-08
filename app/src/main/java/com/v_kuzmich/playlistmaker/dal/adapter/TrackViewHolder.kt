package com.v_kuzmich.playlistmaker.dal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.v_kuzmich.playlistmaker.R
import com.v_kuzmich.playlistmaker.dal.model.Track
import com.v_kuzmich.playlistmaker.helper.UiHelper
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder (
    parent: ViewGroup,
    itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.track_search_list_item, parent, false)
) : RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val artwork: ImageView = itemView.findViewById(R.id.artwork)

    fun bind(item: Track) {
        val uiHelper = UiHelper()

        trackName.text = item.trackName.trim()
        artistName.text = item.artistName.trim()
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis.toInt())
        Glide.with(itemView)
            .load(item.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(uiHelper.dpToPx(2f, itemView.context)))
            .into(artwork)
        artistName.requestLayout()
    }

}