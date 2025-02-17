package com.ptk.movierating2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptk.movierating2.db.dao.MoviesDao
import com.ptk.movierating2.db.dao.MoviesDetailDao
import com.ptk.movierating2.db.entity.MovieDetailEntity
import com.ptk.movierating2.db.entity.MoviesEntity


@Database(
    entities = [
        MoviesEntity::class, MovieDetailEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDB : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getMoviesDetailDao(): MoviesDetailDao

}