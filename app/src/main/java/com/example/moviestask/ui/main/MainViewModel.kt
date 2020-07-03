package com.example.moviestask.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviestask.db.AppDatabase
import com.example.moviestask.entity.Movie
import com.example.moviestask.repository.MainRepository
import com.example.paging.MovieDataSourceFactory


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = this.javaClass.simpleName

    private val movieDao = AppDatabase.getDatabase(application).movieDao()
    private val repo = MainRepository(movieDao)

    val pagedListLiveData: LiveData<PagedList<Movie>>//observed in main Activity

    init {
//page list config
    val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()
        val movieDataSource = MovieDataSourceFactory(repo.dataSource())
        //return pagedList liveData
        pagedListLiveData = LivePagedListBuilder(movieDataSource, pagedListConfig).build()

    }

}
