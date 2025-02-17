package com.ptk.movierating2.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tbl_movies_detail")
data class MovieDetailEntity(

    @ColumnInfo @SerialName("title")
    var title: String? = null,

    @ColumnInfo @SerialName("backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo @SerialName("genres")
    var genres: String? = null,

    @ColumnInfo @SerialName("production_countries")
    var productionCountries: String? = null,

    @ColumnInfo @SerialName("vote_count")
    var voteCount: Int? = null,

    @ColumnInfo @SerialName("overview")
    var overview: String? = null,

    @ColumnInfo @SerialName("runtime")
    var runtime: Int? = null,

    @ColumnInfo @SerialName("spoken_languages")
    var spokenLanguages: String? = null,

    @ColumnInfo @SerialName("release_date")
    var releaseDate: String? = null,

    @ColumnInfo @SerialName("is_fav")
    var isFav: Boolean = false,

    @ColumnInfo @SerialName("id")
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
)