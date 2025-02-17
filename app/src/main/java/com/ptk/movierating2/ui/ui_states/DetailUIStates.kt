package com.ptk.movierating2.ui.ui_states

import com.ptk.movierating2.db.entity.MovieDetailEntity


data class DetailUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,

    val detailResponseModel: MovieDetailEntity? = null,

    val recompose: Boolean = false,

    )