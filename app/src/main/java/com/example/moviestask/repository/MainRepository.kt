package com.example.moviestask.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.moviestask.db.MovieDao
import com.example.moviestask.entity.Movie
import com.example.moviestask.network.api.googleutil.ApiResponse
import com.example.moviestask.network.api.googleutil.AppExecutors
import com.example.moviestask.network.api.googleutil.NetworkBoundResource
import com.example.moviestask.network.api.googleutil.Resource
import com.example.moviestask.network.api.retrofit.NetworkServiceGenerator
import com.example.moviestask.network.networkresponsemodel.DiscoverMovieResponse
import com.example.paging.MoviePagedKeyedDataSource

class MainRepository(val movieDao: MovieDao) {
    private val TAG = this.javaClass.simpleName


    private val DATA_FETCHING_INTERVAL = 60 * 1000 //5 seconds
    private var mLastFetchedDataTimeStamp: Long = 0
    private val discoverMovieService = NetworkServiceGenerator.getDiscoverMovieService()


    fun loadMovies(page: Int): LiveData<Resource<MutableList<Movie>>> {
        Log.d(TAG, "loadMovies $page")
        mLastFetchedDataTimeStamp = System.currentTimeMillis()
        return object : NetworkBoundResource<MutableList<Movie>, DiscoverMovieResponse>() {
            override fun saveCallResult(item: DiscoverMovieResponse) {
                item.results.map { it.page=page }
                movieDao.insertMovieList(movies = item.results)
            }
            override fun shouldFetch(data: MutableList<Movie>?) = data == null || data.isEmpty()
            override fun loadFromDb() = movieDao.getMovieListLiveData(page)
            override fun createCall(): LiveData<ApiResponse<DiscoverMovieResponse>> {
                return discoverMovieService.fetchDiscoverMovieByPage(page)
            }

        }.asLiveData()
    }

    fun dataSource(it: MutableList<Movie>?): MoviePagedKeyedDataSource<Int, Movie> {
        return object : MoviePagedKeyedDataSource<Int, Movie>() {
            //this will be called once to load the initial data
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
                Log.d(TAG, "AMovieDataSource: loadInitial()")
                movieDao.getMovieList(FIRST_PAGE).also {
                    loadMovies(FIRST_PAGE)
                    callback.onResult(it, null, FIRST_PAGE)
                }
            }

            //this will load the next page
            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
                var currentPage = params.key + 1

                movieDao.getMovieList(currentPage).also {
                    Log.d(TAG, "//loadAfter() ${currentPage}")
                    loadMovies(currentPage)

                    callback.onResult(it, ++currentPage)
                }
            }

            //this will load the previous page
            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
                //if the current page is greater than one we are decrementing the page number
                //else there is no previous page
                val adjacentKey = if (params.key > 1) params.key - 1 else 1
                Log.d(TAG, "AMovieDataSource: loadBefore()")
                movieDao.getMovieList(adjacentKey).also {
                    loadMovies(adjacentKey)

                    if (it != null) {

                        //passing the loaded data and the previous page key
                        callback.onResult(it, adjacentKey)
                    }
                }
            }

        }

    }
    private fun dataTimerRefresh(): Boolean =
        if (System.currentTimeMillis() - mLastFetchedDataTimeStamp < DATA_FETCHING_INTERVAL) {
            Log.d(TAG, "Not fetching from network because interval didn't reach")
            false
        } else {
            Log.d(TAG, "//fetching from network because interval reach")
            true
        }
    private fun dBDirectInertion(mappedList: List<Movie>, currentPage: Int) {
        AppExecutors.diskIO().execute {
            mappedList.map { it.page = currentPage }
            movieDao.insertMovieList(mappedList)
        }
    }
}