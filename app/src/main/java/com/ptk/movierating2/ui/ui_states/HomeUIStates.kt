package com.ptk.movierating2.ui.ui_states

import com.ptk.movierating2.db.entity.MoviesEntity


data class HomeUIStates(

    val showLoadingDialog: Boolean = false,
    val isShowNoConnectionDialog: Boolean = false,

    val errorMessage: String = "",

    val popularList: ArrayList<MoviesEntity> = arrayListOf(),
    val upcomingList: ArrayList<MoviesEntity> = arrayListOf(),

    var refreshing: Boolean = false,

    )