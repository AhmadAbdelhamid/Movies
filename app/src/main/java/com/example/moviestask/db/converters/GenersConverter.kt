package com.example.moviestask.db.converters

import androidx.room.TypeConverter
import com.example.moviestask.entity.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class GenersConverter {

    @TypeConverter
    fun fromGenreList(GenreList: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(GenreList)
    }

    @TypeConverter
    fun toGenreList(s: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson<List<Genre>>(s, type)

    }

}