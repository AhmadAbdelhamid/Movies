package com.example.moviestask.network.api.retrofit

import com.example.moviestask.network.api.googleutil.LiveDataCallAdapterFactory
import com.example.moviestask.common.ApiConstants
import com.example.moviestask.network.api.services.DiscoverMovieService
import com.example.moviestask.network.api.services.MovieService
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkServiceGenerator {

   private fun getHttpClient(): OkHttpClient {
        val interceptorBuilder = OkHttpClient().newBuilder()
            .addInterceptor(apiInterceptor())
            .addInterceptor(loggingInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
        return interceptorBuilder.build()
    }

     private fun retrofitClient(): Retrofit {
       return  Retrofit.Builder().baseUrl(ApiConstants.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(LiveDataCallAdapterFactory())
           .client(getHttpClient()).build()
    }

    fun getDiscoverMovieService(): DiscoverMovieService {
        return retrofitClient().create(DiscoverMovieService::class.java)
    }
    fun getMovieService(): MovieService {
        return retrofitClient().create(MovieService::class.java)
    }



}