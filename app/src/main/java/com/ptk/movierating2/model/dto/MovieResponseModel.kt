package com.ptk.movierating2.model.dto

import com.ptk.movierating2.db.entity.MoviesEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieResponseModel(
    @SerialName("results")
    val results: ArrayList<MoviesEntity>
)