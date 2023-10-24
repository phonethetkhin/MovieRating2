package com.ptk.codigocodemanagement.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponseModel(

    @SerialName("title")
    val title: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genres")
    val genres: List<GenresItem?>? = null,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountriesItem?>? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem?>? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    var isFav: Boolean = false,

    )

@Serializable
data class SpokenLanguagesItem(

    @SerialName("name")
    val name: String? = null,

    @SerialName("iso_639_1")
    val iso6391: String? = null,

    @SerialName("english_name")
    val englishName: String? = null
)


@Serializable
data class ProductionCountriesItem(

    @SerialName("iso_3166_1")
    val iso31661: String? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class GenresItem(

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null
)
