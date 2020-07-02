package com.example.moviestask.db
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviestask.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Query("SELECT * FROM MOVIE WHERE id = :id_")
    fun getMovie(id_: Int): Movie

    @Query("SELECT * FROM Movie WHERE page = :page_")
    fun getMovieListLiveData(page_: Int): LiveData<MutableList<Movie>>

  @Query("SELECT * FROM Movie WHERE page = :page_")
    fun getMovieList(page_: Int): MutableList<Movie>

    @Query("SELECT * FROM Movie WHERE page = :page_")
    fun getMovieListPagedList(page_: Int):DataSource.Factory<Int, Movie>

}