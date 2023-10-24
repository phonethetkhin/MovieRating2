package com.ptk.codigocodemanagement.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.codigocodemanagement.repository.DetailRepository
import com.ptk.codigocodemanagement.ui.ui_states.DetailUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    private val application: Application

) : ViewModel() {
    private val _uiStates = MutableStateFlow(DetailUIStates())
    val uiStates = _uiStates.asStateFlow()


    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            Log.e("DetailResponseModel2", movieId.toString())

            val movieDetail = repository.getMovieDetail(movieId)
            Log.e("DetailResponseModel3", movieDetail.toString())

            _uiStates.update { it.copy(detailResponseModel = movieDetail) }
        }
    }

    fun toggleIsFav(movieId: Int, isFav: Boolean) {
        viewModelScope.launch {
            repository.updateIsFav(movieId, isFav)
            _uiStates.value.detailResponseModel?.isFav = isFav
            _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }
        }
    }


}
