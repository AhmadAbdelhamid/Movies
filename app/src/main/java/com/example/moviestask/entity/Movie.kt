package com.example.moviestask.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity
@Parcelize
data class Movie(
		@PrimaryKey val id: Int=0,
		val title: String?="",
		val tagline: String?="",
		val poster_path: String?="",
		val backdrop_path: String?="",
		val release_date: String?="",
		var videos: MutableList<Video> = ArrayList(),
		var genres: MutableList<Genre> = ArrayList(),
		val vote_count: Int?=0,
		val vote_average: Float= 0f,
		val video: Boolean?=false,
		val adult: Boolean?=false,
		val overview: String?="",
		val status: String?="",
		var page: Int=0,
		val runtime: Int?=0
) : Parcelable
