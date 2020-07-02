package com.example.paging

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestask.common.ApiConstants
import com.example.moviestask.entity.Movie
import com.example.moviestask.ui.details.DetailsActivity
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviePagerViewH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val TAG = this.javaClass.simpleName
    fun bind(movie: Movie?) {
        itemView.run {
            tv_movie_title.text = movie?.title
            Glide.with(context)
                .load(ApiConstants.getPosterUrl(movie?.poster_path))
                .listener(
                    GlidePalette.with(ApiConstants.getPosterUrl(movie?.poster_path))
                        .use(BitmapPalette.Profile.VIBRANT)
                        .intoBackground(item_poster_palette)
                        .crossfade(true)
                )
                .into(iv_movie)

            //goto movie details
            setOnClickListener { v ->
                if (movie != null) {
                    DetailsActivity.startDetailsActivity(v?.context, movie)
                }
            }
        }

    }

}