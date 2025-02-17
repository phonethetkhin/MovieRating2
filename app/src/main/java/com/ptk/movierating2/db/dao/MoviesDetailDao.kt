package com.ptk.movierating2.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.movierating2.db.entity.MovieDetailEntity

@Dao
interface MoviesDetailDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM tbl_movies_detail WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity

    @Query("UPDATE tbl_movies_detail SET isFav = :isFav WHERE id=:movieId")
    suspend fun updateIsFav(movieId: Int, isFav: Boolean)


}