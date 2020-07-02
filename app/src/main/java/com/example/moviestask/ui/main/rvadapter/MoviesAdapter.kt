package com.example.moviestask.ui.main.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviestask.R
import com.example.moviestask.entity.Movie

class MoviesAdapter( ) : Adapter<MoviesViewHolder>() {
    var moviesList= mutableListOf<Movie>()
    set(value) {
        field.clear()
        field.addAll(value)
    notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(this.moviesList[position])
    }
    override fun getItemCount() = this.moviesList.size

}
