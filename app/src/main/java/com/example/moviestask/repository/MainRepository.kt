package com.example.moviestask.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

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

	private val DATA_FETCHING_INTERVAL = 5 * 1000 //5 seconds
	private var mLastFetchedDataTimeStamp: Long = 0
	private val discoverMovieService = NetworkServiceGenerator.getDiscoverMovieService()//retrofit api service

	///load movies from single source
	fun loadMovies(page: Int): LiveData<Resource<MutableList<Movie>>> {

		mLastFetchedDataTimeStamp = System.currentTimeMillis()
		return object : NetworkBoundResource<MutableList<Movie>, DiscoverMovieResponse>() {
			override fun saveCallResult(item: DiscoverMovieResponse) {
				item.results.map { it.page = page }
				movieDao.insertMovieList(movies = item.results)
			}

			override fun shouldFetch(data: MutableList<Movie>?) = data == null || data.isEmpty()
			override fun loadFromDb() = movieDao.getMovieListLiveData(page)
			override fun createCall(): LiveData<ApiResponse<DiscoverMovieResponse>> {
				return discoverMovieService.fetchDiscoverMovieByPage(page)
			}

		}.asLiveData()
	}


	///update PageList
	fun dataSource(): MoviePagedKeyedDataSource<Int, Movie> {
		return object : MoviePagedKeyedDataSource<Int, Movie>() {
			//this will be called once to load the initial data
			override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

				loadMovies(FIRST_PAGE).map {
					it.data?.let { moviesList -> callback.onResult(moviesList, null, FIRST_PAGE) }

				}

			}

			//this will load the next page
			override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
				var currentPage = params.key + 1

				loadMovies(currentPage).map {

					it.data?.let { moviesList -> callback.onResult(moviesList, ++currentPage) }
				}


			}

			//this will load the previous page
			override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
				//if the current page is greater than one we are decrementing the page number
				//else there is no previous page
				val previousPage = if (params.key > 1) params.key - 1 else 1
				Log.d(TAG, "AMovieDataSource: loadBefore()")

					loadMovies(previousPage).map {
                        //passing the loaded data and the previous page key
                        it.data?.let { it1 -> callback.onResult(it1, previousPage) }
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