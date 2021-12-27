package com.zaus_app.moviefrumy.di.modules

import android.content.Context
import com.zaus_app.moviefrumy.data.db.DatabaseHelper
import com.zaus_app.moviefrumy.data.db.DatabaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = com.zaus_app.moviefrumy.data.MainRepository


    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)


    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseHelper: DatabaseHelper) = DatabaseRepository(databaseHelper)

}