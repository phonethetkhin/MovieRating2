package com.ptk.codigocodemanagement.repository

import android.app.Application
import com.ptk.codigocodemanagement.R
import com.ptk.codigocodemanagement.db.dao.MoviesDao
import com.ptk.codigocodemanagement.db.dao.MoviesDetailDao
import com.ptk.codigocodemanagement.db.entity.MovieDetailEntity
import com.ptk.codigocodemanagement.db.entity.MoviesEntity
import com.ptk.codigocodemanagement.model.RemoteResource
import com.ptk.codigocodemanagement.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val moviesDao: MoviesDao,
    private val moviesDetailDao: MoviesDetailDao,
    private val application: Application,
) {
    //========================================API===================================================

    fun getPopularMovies(
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getPopularMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    fun getUpcomingMovies(
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getUpcomingMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    fun getMovieDetail(
        movieId: Int
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getMovieDetail(movieId)
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    //=========================================DB===================================================

    suspend fun insertAllMovies(movies: List<MoviesEntity>) = moviesDao.insertAllMovies(movies)

    suspend fun findMovie(movieId: Int) = moviesDao.findMovie(movieId)
    suspend fun updateUpcomingStatus(movieId: Int) = moviesDao.updateUpcomingStatus(movieId)
    suspend fun getAllMovies() = moviesDao.getAllMovies()
    suspend fun getAllPopularMovies() = moviesDao.getAllPopularMovies()
    suspend fun getAllUpcomingMovies() = moviesDao.getAllUpcomingMovies()

    //movie detail dao

    suspend fun insertMovieDetail(movieDetailEntity: MovieDetailEntity) =
        moviesDetailDao.insertMovieDetail(movieDetailEntity)
}
