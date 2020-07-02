package com.example.moviestask.common

object ApiConstants {
    const val API_KEY= "07b2d0197f8f353e6e6a2d77588b03e6"
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

    fun getBaseUrl(): String = BASE_URL
    fun getPosterUrl (moviePoster: String?) :String = "$BASE_POSTER_URL$moviePoster"
    fun getBackDropUrl (movieBackDropPath:String?) :String = "$BASE_BACKDROP_PATH$movieBackDropPath"
    fun getVideoUrl (movieVideo:String) :String = "$YOUTUBE_VIDEO_URL$movieVideo"
    fun getVideoThumbUrl (videoThumb:String) :String = "$YOUTUBE_THUMBNAIL_URL$videoThumb/default.jpg"
}