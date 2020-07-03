package com.example.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviestask.entity.Movie


class MovieDataSourceFactory(private val moviePagedKeyedDataSource: MoviePagedKeyedDataSource<Int, Movie>): DataSource.Factory<Int, Movie>() {

    //creating the mutable live data
    private val _pagedDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    private val _paged= MutableLiveData<Int>()

   override fun create(): MoviePagedKeyedDataSource<Int, Movie> {
        val pagedDataSource = moviePagedKeyedDataSource //test
        //posting the datasource to get the values
       _pagedDataSource.postValue(pagedDataSource)
        //returning the datasource
        return pagedDataSource
    }


    fun getLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Movie>> {
        return _pagedDataSource
    }


}