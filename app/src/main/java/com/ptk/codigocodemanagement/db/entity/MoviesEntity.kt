package com.ptk.codigocodemanagement.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tbl_movies")
data class MoviesEntity(
    @ColumnInfo @SerialName("title")
    val title: String? = null,

    @ColumnInfo @SerialName("poster_path")
    val posterPath: String? = null,

    @ColumnInfo @SerialName("vote_average")
    val voteAverage: Double? = null,

    @ColumnInfo @SerialName("is_popular_movie")
    var isPopularMovie: Boolean = false,

    @ColumnInfo @SerialName("is_upcoming_movie")
    var isUpcomingMovie: Boolean = false,

    @ColumnInfo @SerialName("is_fav")
    var isFav: Boolean = false,

    @ColumnInfo @SerialName("id")
    @PrimaryKey(autoGenerate = false) val id: Int = 0,

    )