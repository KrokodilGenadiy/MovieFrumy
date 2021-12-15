package com.zaus_app.moviefrumy

import android.app.Application
import com.zaus_app.moviefrumy.di.DI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Прикрепляем контекст
            androidContext(this@App)
            //(Опционально) подключаем зависимость
            //androidLogger()
            //Инициализируем модули
            modules(listOf(DI.mainModule))
        }
    }
}