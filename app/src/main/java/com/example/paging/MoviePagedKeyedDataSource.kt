package com.example.paging

import androidx.paging.PageKeyedDataSource
import com.example.moviestask.entity.Movie

abstract class MoviePagedKeyedDataSource<T, U> : PageKeyedDataSource<Int, Movie>() {
    companion object {
        //we will start from the first page which is 1
        internal val FIRST_PAGE = 1
        val PAGE_SIZE = 20
    }
}