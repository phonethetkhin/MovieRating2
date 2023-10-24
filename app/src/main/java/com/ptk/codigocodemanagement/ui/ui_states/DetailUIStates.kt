package com.ptk.codigocodemanagement.ui.ui_states

import com.ptk.codigocodemanagement.db.entity.MovieDetailEntity


data class DetailUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,

    val detailResponseModel: MovieDetailEntity? = null,

    )