package com.zaus_app.moviefrumy

import android.app.Application
import com.zaus_app.moviefrumy.di.AppComponent
import com.zaus_app.moviefrumy.di.DaggerAppComponent


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}