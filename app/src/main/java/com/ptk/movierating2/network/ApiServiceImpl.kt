package com.ptk.movierating2.network


import com.ptk.movierating2.model.dto.MovieDetailResponseModel
import com.ptk.movierating2.model.dto.MovieResponseModel
import com.ptk.movierating2.util.Constants.BASE_URL
import com.ptk.movierating2.util.Constants.TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
public class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) : ApiService {

    override suspend fun getPopularMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getPopularMovies)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getUpcomingMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getUpcomingMovies)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponseModel = client.get {
        url(BASE_URL + APIRoutes.getMovieDetail + movieId)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }


}