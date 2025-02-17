package com.ptk.movierating2.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesDataStore @Inject constructor(private val application: Application) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("imsDataStore")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch")

    }

    val isFirstLaunch: Flow<Boolean?> = application.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_LAUNCH] ?: true
        }

    suspend fun saveIsFirstLaunch(isFirstLaunch: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }


}