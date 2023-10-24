package com.ptk.codigocodemanagement.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.codigocodemanagement.datastore.MoviesDataStore
import com.ptk.codigocodemanagement.db.MovieDB
import com.ptk.codigocodemanagement.db.entity.MovieDetailEntity
import com.ptk.codigocodemanagement.db.entity.MoviesEntity
import com.ptk.codigocodemanagement.model.RemoteResource
import com.ptk.codigocodemanagement.repository.HomeRepository
import com.ptk.codigocodemanagement.ui.ui_states.HomeUIStates
import com.ptk.codigocodemanagement.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val application: Application,
    private val dataStore: MoviesDataStore,
    private val movieDB: MovieDB,

    ) : ViewModel() {
    private val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=========================================Utility==============================================

    fun isInternetAvailable(context: Context): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    /* fun toggleFav(movieId: Int) {
         val movieModel = _uiStates.value.popularList.find { it.id == movieId }
         val movieModel3 = _uiStates.value.upcomingList.find { it.id == movieId }

         _uiStates.update {
             it.copy(popularList = _uiStates.value.popularList.mapIndexed { index, details ->
                 if (_uiStates.value.popularList.indexOf(movieModel) == index) details.copy(
                     isFav = !details.isFav
                 )
                 else details
             } as ArrayList<MoviesEntity>)
         }

         _uiStates.update {
             it.copy(upcomingList = _uiStates.value.upcomingList.mapIndexed { index, details ->
                 if (_uiStates.value.upcomingList.indexOf(movieModel3) == index) details.copy(
                     isFav = !details.isFav
                 )
                 else details
             } as ArrayList<MoviesEntity>)
         }

     }*/

    fun checkConnection(
    ) {
        if (uiStates.value.isShowNoConnectionDialog) {
            _uiStates.update { it.copy(isShowNoConnectionDialog = false) }
        }
        viewModelScope.launch {
            if (dataStore.isFirstLaunch.first()!!) {
                val isConnected = isInternetAvailable(application)
                if (isConnected) {
                    Log.e("Sequence", "1")
                    withContext(Dispatchers.IO) { getPopularMovies() }
                    Log.e("Sequence", "5")

                    withContext(Dispatchers.IO) { getUpcomingList() }
                    Log.e("Sequence", "9")

                    val allMovies = repository.getAllMovies()
                    Log.e("Sequence", "10")

                    allMovies.forEach {
                        Log.e("Sequence", "11")

                        withContext(Dispatchers.IO) { getMovieDetail(it.id) }
                        Log.e("Sequence", "14")

                    }
                    Log.e("Sequence", "15")
                    fetchMoviesListFromDB()
                    dataStore.saveIsFirstLaunch(false)

                } else {
                    toggleIsShowNoConnectionDialog(true)
                }
            } else {

                fetchMoviesListFromDB()
            }
        }
    }

    //=====================================StatesManagement=========================================

    fun toggleIsShowNoConnectionDialog(isShowNoConnectionDialog: Boolean) {
        _uiStates.update { it.copy(isShowNoConnectionDialog = isShowNoConnectionDialog) }
    }


    //=========================================API==================================================

    suspend fun getPopularMovies(

    ) = viewModelScope.async {
        Log.e("Sequence", "2")

        withContext(Dispatchers.IO) { movieDB.clearAllTables() }
        repository.getPopularMovies().collectLatest { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    Log.e("Sequence", "3")

                    val popularMovies = remoteResource.data.results
                    popularMovies.onEach { it.isPopularMovie = true }
                    Log.e("TypesofMovies", remoteResource.data.results.size.toString())
                    Log.e("TypesofMovies", remoteResource.data.results.toString())
                    insertAllMovies(popularMovies)
                    Log.e("Sequence", "4")

                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }
    }.await()

    suspend fun getUpcomingList(

    ) = viewModelScope.async {
        Log.e("Sequence", "6")

        repository.getUpcomingMovies().collectLatest { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    Log.e("Sequence", "7")
                    val upcomingList = arrayListOf<MoviesEntity>()
                    val upcomingMovies = remoteResource.data.results
                    upcomingMovies.forEach {
                        val movieEntity = repository.findMovie(it.id)
                        if (movieEntity != null) {
                            repository.updateUpcomingStatus(movieEntity.id)
                        } else {
                            it.isUpcomingMovie = true
                            upcomingList.add(it)
                        }
                    }
                    Log.e("UpcomingList", "$upcomingList")

                    insertAllMovies(upcomingList)
                    Log.e("Sequence", "8")

                    Log.e("TypesofMovies", remoteResource.data.results.size.toString())
                    Log.e("TypesofMovies", remoteResource.data.results.toString())

                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }
    }.await()

    suspend fun getMovieDetail(
        movieId: Int,
    ) = viewModelScope.async {
        Log.e("Sequence", "12")

        repository.getMovieDetail(movieId).collectLatest { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    val movieDetailData = remoteResource.data

                    val movieDetailEntity = MovieDetailEntity()
                    movieDetailEntity.id = movieDetailData.id ?: 0
                    movieDetailEntity.title = movieDetailData.title
                    movieDetailEntity.backdropPath = movieDetailData.backdropPath
                    movieDetailEntity.genres = movieDetailData.genres?.map { movieDetailData.title }
                        ?.joinToString(separator = ",")

                    movieDetailEntity.productionCountries =
                        movieDetailData.productionCountries?.get(0)?.iso31661 ?: "-"
                    movieDetailEntity.voteCount = movieDetailData.voteCount
                    movieDetailEntity.overview = movieDetailData.overview
                    movieDetailEntity.runtime = movieDetailData.runtime
                    movieDetailEntity.spokenLanguages =
                        movieDetailData.spokenLanguages?.get(0)?.name
                    movieDetailEntity.releaseDate = movieDetailData.releaseDate

                    repository.insertMovieDetail(movieDetailEntity)

                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }
    }.await()


    //=========================================DB===================================================

    suspend fun insertAllMovies(movies: List<MoviesEntity>) {
        viewModelScope.launch { repository.insertAllMovies(movies) }
    }

    suspend fun fetchMoviesListFromDB() {
        Log.e("Sequence", "16")

        viewModelScope.launch {
            val popularMovies = repository.getAllPopularMovies()
            val upcomingMovies = repository.getAllUpcomingMovies()
            Log.e("Sequence", "17")

            _uiStates.update {
                it.copy(
                    popularList = popularMovies.toCollection(ArrayList()),
                    upcomingList = upcomingMovies.toCollection(ArrayList())
                )
            }
        }
    }


}
