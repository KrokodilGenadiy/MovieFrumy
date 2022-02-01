package com.zaus_app.moviefrumy.di.modules

import android.content.Context
import com.zaus_app.moviefrumy.data.FavoritesRepository
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.TmdbApi
import com.zaus_app.moviefrumy.domain.Interactor
import com.zaus_app.moviefrumy.utils.PreferenceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository,favoritesRepository: FavoritesRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) = Interactor(repo = repository,favoritesRepo = favoritesRepository, retrofitService = tmdbApi, preferences = preferenceProvider)
}