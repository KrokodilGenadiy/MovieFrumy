package com.zaus_app.moviefrumy.di.modules

import android.content.Context
import androidx.room.Room
import com.zaus_app.moviefrumy.data.FavoritesRepository
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.dao.FilmDao
import com.zaus_app.moviefrumy.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db2"
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)

    @Provides
    @Singleton
    fun provideFavoritesRepository() = FavoritesRepository()

}