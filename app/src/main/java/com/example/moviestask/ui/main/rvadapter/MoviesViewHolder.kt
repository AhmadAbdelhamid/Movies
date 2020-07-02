package com.example.moviestask.ui.main.rvadapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestask.common.ApiConstants
import com.example.moviestask.entity.Movie
import com.example.moviestask.ui.details.DetailsActivity
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder( view: View) : RecyclerView.ViewHolder(view.rootView) {

    val TAG = this.javaClass.simpleName
    fun bind(movie: Movie) {
        itemView.run  {
            tv_movie_title.text = movie.title
            Glide.with(this)
                .load(ApiConstants.getPosterUrl(movie.poster_path))
                .listener(GlidePalette.with(ApiConstants.getPosterUrl(movie.poster_path))
                    .use(BitmapPalette.Profile.VIBRANT)
                    .intoBackground(item_poster_palette)
                    .crossfade(true))
                .into(iv_movie)
                setOnClickListener {v-> DetailsActivity.startDetailsActivity(v?.context, movie)  }
        }.also { Log.d(TAG,"[${movie.title}: ${movie.id}]") }

    }


}

