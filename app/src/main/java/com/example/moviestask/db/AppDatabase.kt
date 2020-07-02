package com.example.moviestask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviestask.entity.Movie
import com.example.moviestask.db.converters.GenersConverter
import com.example.moviestask.db.converters.VideoConverter

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(value = [(VideoConverter::class), (GenersConverter::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "MovieDB").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}