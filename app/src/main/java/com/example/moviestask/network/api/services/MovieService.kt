package com.example.moviestask.network.api.services

import androidx.lifecycle.LiveData
import com.example.moviestask.network.api.googleutil.ApiResponse
import com.example.moviestask.network.networkresponsemodel.VideoListResponse
import com.example.moviestask.entity.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/{movie_id}")
    fun fetchMovieDetails(@Path("movie_id") movie_ID: Int): LiveData<ApiResponse<Movie>>

    @GET("movie/{movie_id}/videos")
    fun fetchVideoList(@Path("movie_id") movie_ID: Int): LiveData<ApiResponse<VideoListResponse>>
}