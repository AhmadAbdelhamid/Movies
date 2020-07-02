package com.example.moviestask.network.networkresponsemodel

import com.example.moviestask.entity.Video

data class VideoListResponse(
    val id: Int,
    val results: List<Video>
)