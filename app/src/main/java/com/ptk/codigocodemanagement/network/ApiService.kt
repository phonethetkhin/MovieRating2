package com.ptk.codigocodemanagement.network

import com.ptk.codigocodemanagement.model.dto.MovieDetailResponseModel
import com.ptk.codigocodemanagement.model.dto.MovieResponseModel


public interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponseModel


}