package com.example.moviestask.db.converters

import androidx.room.TypeConverter
import com.example.moviestask.entity.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class VideoConverter {

    @TypeConverter
    fun fromVideoList(videoList: List<Video>): String {
        val gson = Gson()
        return gson.toJson(videoList)
    }

    @TypeConverter
    fun toVideoList(s: String): List<Video> {
        val type = object : TypeToken<List<Video>>() {}.type
        return Gson().fromJson<List<Video>>(s, type)

    }

}