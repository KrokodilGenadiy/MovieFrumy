package com.zaus_app.moviefrumy.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = com.zaus_app.moviefrumy.data.MainRepository
}