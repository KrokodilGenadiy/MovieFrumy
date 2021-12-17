package com.zaus_app.moviefrumy

import android.app.Application
import com.zaus_app.moviefrumy.di.AppComponent
import com.zaus_app.moviefrumy.di.DaggerAppComponent
import com.zaus_app.moviefrumy.di.modules.DatabaseModule
import com.zaus_app.moviefrumy.di.modules.DomainModule
import com.zaus_app.moviefrumy.di.modules.RemoteModule


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}