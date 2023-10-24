package com.ptk.codigocodemanagement.model.dto

import com.ptk.codigocodemanagement.db.entity.MoviesEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieResponseModel(
    @SerialName("results")
    val results: ArrayList<MoviesEntity>
)