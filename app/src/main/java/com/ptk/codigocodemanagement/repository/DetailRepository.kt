package com.ptk.codigocodemanagement.repository

import android.app.Application
import com.ptk.codigocodemanagement.db.dao.MoviesDetailDao
import com.ptk.codigocodemanagement.network.ApiService
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val apiService: ApiService,
    private val moviesDetailDao: MoviesDetailDao,
    private val application: Application,
) {


    suspend fun getMovieDetail(movieId: Int) = moviesDetailDao.getMovieDetail(movieId)


}
