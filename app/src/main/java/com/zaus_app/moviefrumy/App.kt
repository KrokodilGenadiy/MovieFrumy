package com.zaus_app.moviefrumy

import android.app.Application
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.domain.Interactor

class App : Application() {
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Инициализируем репозиторий
        val repo = MainRepository
        //Инициализируем интерактор
        interactor = Interactor(repo)
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}