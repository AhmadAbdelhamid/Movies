package com.example.moviestask.ui.details.rvadapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestask.common.ApiConstants
import com.example.moviestask.entity.Video
import kotlinx.android.synthetic.main.item_video.view.*

class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {
    private val TAG = this.javaClass.simpleName
    fun bind(video: Video) {
        itemView.run {
            Glide.with(context)
                .load(ApiConstants.getVideoThumbUrl(video.key))
                .into(item_video_cover)
            setOnClickListener { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.getVideoUrl(video.key)))) }
        }.also { Log.d(TAG,video.toString()) }

    }
}