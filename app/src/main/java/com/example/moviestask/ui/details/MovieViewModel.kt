package com.example.moviestask.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviestask.db.AppDatabase
import com.example.moviestask.db.MovieDao
import com.example.moviestask.entity.Movie
import com.example.moviestask.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var movieCopy: Movie = Movie()
    private val TAG = this.javaClass.simpleName


    private val repo = MovieRepository
    val _movieIdToGetVideosLiveData = MutableLiveData<Int>()
    private val movieDao: MovieDao = AppDatabase.getDatabase(application).movieDao()


    val movieTrailersLiveData = _movieIdToGetVideosLiveData.switchMap { movieID ->
        repo.loadMovieTrailers(movieID, movieDao)
    }

//    val movieLiveData = _movieIDLiveData.switchMap { movieID -> repo.loadMovieDetails(movieID, movieDao) }

    fun setMovie_ID() {
        _movieIdToGetVideosLiveData.value = movieCopy.id
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()

    }
}