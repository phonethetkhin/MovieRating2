package com.ptk.movierating2.repository

import android.app.Application
import com.ptk.movierating2.db.dao.MoviesDetailDao
import com.ptk.movierating2.network.ApiService
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val apiService: ApiService,
    private val moviesDetailDao: MoviesDetailDao,
    private val application: Application,
) {


    suspend fun getMovieDetail(movieId: Int) = moviesDetailDao.getMovieDetail(movieId)

    suspend fun updateIsFav(movieId: Int, isFav: Boolean) =
        moviesDetailDao.updateIsFav(movieId, isFav)


}
