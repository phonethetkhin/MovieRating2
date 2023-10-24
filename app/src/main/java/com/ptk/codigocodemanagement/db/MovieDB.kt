package com.ptk.codigocodemanagement.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptk.codigocodemanagement.db.dao.MoviesDao
import com.ptk.codigocodemanagement.db.dao.MoviesDetailDao
import com.ptk.codigocodemanagement.db.entity.MovieDetailEntity
import com.ptk.codigocodemanagement.db.entity.MoviesEntity


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