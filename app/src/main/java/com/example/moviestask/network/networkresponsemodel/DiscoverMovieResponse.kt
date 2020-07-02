package com.example.moviestask.network.networkresponsemodel

import com.example.moviestask.entity.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)