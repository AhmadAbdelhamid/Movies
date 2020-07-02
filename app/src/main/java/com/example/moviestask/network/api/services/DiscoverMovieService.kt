package com.example.moviestask.network.api.services

import androidx.lifecycle.LiveData
import com.example.moviestask.network.api.googleutil.ApiResponse
import com.example.moviestask.network.networkresponsemodel.DiscoverMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieService {

    @GET("discover/movie?sort_by=popularity.desc")
    fun fetchDiscoverMovie(): LiveData<ApiResponse<DiscoverMovieResponse>>

    @GET("discover/movie?sort_by=popularity.desc")
    fun fetchDiscoverMovieByPage(@Query("page") page: Int): LiveData<ApiResponse<DiscoverMovieResponse>>

    @GET("discover/movie?sort_by=popularity.desc")
    fun fetchserviceDiscoverMovieByPage(@Query("page") page: Int): Call<DiscoverMovieResponse>

}
