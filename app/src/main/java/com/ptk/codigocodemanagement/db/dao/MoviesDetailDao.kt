package com.ptk.codigocodemanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.codigocodemanagement.db.entity.MovieDetailEntity

@Dao
interface MoviesDetailDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM tbl_movies_detail WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity


}