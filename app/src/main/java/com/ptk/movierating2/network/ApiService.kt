package com.ptk.movierating2.network

import com.ptk.movierating2.model.dto.MovieDetailResponseModel
import com.ptk.movierating2.model.dto.MovieResponseModel


public interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponseModel


}