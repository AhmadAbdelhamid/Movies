package com.example.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.moviestask.R
import com.example.moviestask.entity.Movie


class MoviePagedListAdapter: PagedListAdapter<Movie, MoviePagerViewH>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_movie, parent, false)

    return MoviePagerViewH(view)
    }

    override fun onBindViewHolder(holder: MoviePagerViewH, position: Int) {
        holder.bind(getItem(position))

    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}