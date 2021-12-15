package com.zaus_app.moviefrumy.di.modules

import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.TmdbApi
import com.zaus_app.moviefrumy.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}