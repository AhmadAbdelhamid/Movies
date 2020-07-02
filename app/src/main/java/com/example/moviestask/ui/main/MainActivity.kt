package com.example.moviestask.ui.main

import android.content.ComponentCallbacks2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.moviestask.R
import com.example.moviestask.ui.main.rvadapter.MoviesAdapter
import com.example.paging.MoviePagedListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ComponentCallbacks2 {
    val TAG = this.javaClass.simpleName

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviePagedListAdapter: MoviePagedListAdapter

    val model: MainViewModel by viewModels() //androidx.activity-ktx dependencies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesAdapter = MoviesAdapter()
        moviePagedListAdapter = MoviePagedListAdapter()
        initUI()
        endlessScrollObserver()

    }
    private fun initUI() {
        rv_movies.apply {
            adapter = moviePagedListAdapter
            setHasFixedSize(true)
        }
    }
    private fun endlessScrollObserver() {
        //get endless scroll of movies
        model.pagedListLiveData.observe(this, Observer {
            moviePagedListAdapter.submitList(it)
        })
    }



}





