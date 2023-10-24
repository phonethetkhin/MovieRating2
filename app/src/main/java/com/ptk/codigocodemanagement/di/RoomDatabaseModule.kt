package com.ptk.codigocodemanagement.di

import android.content.Context
import androidx.room.Room
import com.ptk.codigocodemanagement.db.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideIMSDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MovieDB::class.java,
        "movies.db"
    ).build()

    @Singleton
    @Provides
    fun providesMoviesDao(db: MovieDB) = db.getMoviesDao()

    @Singleton
    @Provides
    fun providesMovieDetailDao(db: MovieDB) = db.getMoviesDetailDao()


}