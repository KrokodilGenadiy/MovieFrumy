package com.zaus_app.moviefrumy.di.modules

import android.content.Context
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.TmdbApi
import com.zaus_app.moviefrumy.domain.Interactor
import com.zaus_app.moviefrumy.utils.PreferenceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
//Передаем контекст для SharedPreferences через конструктор
class DomainModule(val context: Context) {
    //Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    //Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) = Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}