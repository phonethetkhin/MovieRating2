package com.ptk.codigocodemanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.codigocodemanagement.db.entity.MoviesEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertAllMovies(movies: List<MoviesEntity>)

    @Query("SELECT * FROM tbl_movies WHERE id=:movieId")
    suspend fun findMovie(movieId: Int): MoviesEntity?

    @Query("UPDATE tbl_movies SET isUpcomingMovie = 1 WHERE id =:movieId")
    suspend fun updateUpcomingStatus(movieId: Int)

    @Query("SELECT * FROM tbl_movies")
    suspend fun getAllMovies(): List<MoviesEntity>

    @Query("SELECT * FROM tbl_movies WHERE isPopularMovie = 1")
    suspend fun getAllPopularMovies(): List<MoviesEntity>

    @Query("SELECT * FROM tbl_movies WHERE isUpcomingMovie = 1")
    suspend fun getAllUpcomingMovies(): List<MoviesEntity>

}