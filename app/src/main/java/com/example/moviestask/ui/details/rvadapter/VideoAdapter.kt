package com.example.moviestask.ui.details.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviestask.R
import com.example.moviestask.entity.Video

class VideoAdapter( ) : Adapter<VideoViewHolder>() {
    var videoList= mutableListOf<Video>()
    set(value) {
        field.clear()
        field.addAll(value)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(this.videoList[position])
    }
    override fun getItemCount() = this.videoList.size

}
