package com.example.moviestask.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviestask.db.MovieDao
import com.example.moviestask.entity.Movie
import com.example.moviestask.entity.Video
import com.example.moviestask.network.api.googleutil.ApiResponse
import com.example.moviestask.network.api.googleutil.AppExecutors
import com.example.moviestask.network.api.googleutil.NetworkBoundResource
import com.example.moviestask.network.api.googleutil.Resource
import com.example.moviestask.network.api.retrofit.NetworkServiceGenerator
import com.example.moviestask.network.networkresponsemodel.VideoListResponse
object MovieRepository {
    private val TAG = this.javaClass.simpleName
    private val movieService = NetworkServiceGenerator.getMovieService()

    fun loadMovieDetails(movie_ID: Int, movieDao: MovieDao): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, Movie>() {

            override fun saveCallResult(movie: Movie) { movieDao.insertMovie(movie) }

            override fun shouldFetch(movie: Movie?) = movie == null

            override fun loadFromDb(): LiveData<Movie> {
                val data: MutableLiveData<Movie> = MutableLiveData()
                AppExecutors.diskIO().execute {
                    //get movie from db in back ground thread
                    val movie = movieDao.getMovie(id_ = movie_ID)
                    data.postValue(movie)
                }
                return data
            }
            override fun createCall() = movieService.fetchMovieDetails(movie_ID)
        }.asLiveData()
    }

    fun loadMovieTrailers(movie_ID: Int, movieDao: MovieDao): LiveData<Resource<MutableList<Video>>> {
        return object : NetworkBoundResource<MutableList<Video>, VideoListResponse>() {
            override fun saveCallResult(videos: VideoListResponse) {
                val movie = movieDao.getMovie(id_ = movie_ID)
                movie.videos = videos.results as MutableList<Video>
                movieDao.updateMovie(movie = movie)
            }

            override fun shouldFetch(videos: MutableList<Video>?) = videos == null || videos.isEmpty()
            override fun loadFromDb(): LiveData<MutableList<Video>> {
                val data: MutableLiveData<MutableList<Video>> = MutableLiveData()
                AppExecutors.diskIO().execute {
                    val movie = movieDao.getMovie(id_ = movie_ID)
                    data.postValue(movie.videos)
                }
                return data
            }
            override fun createCall(): LiveData<ApiResponse<VideoListResponse>> = movieService.fetchVideoList(movie_ID)
        }.asLiveData()
    }


}