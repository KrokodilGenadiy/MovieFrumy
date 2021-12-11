package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Film
import com.zaus_app.moviefrumy.domain.Interactor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class FavoriteFragmentViewModel : ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    private val interactor: Interactor by inject()

    init {
        val films = interactor.getFavoriteFilms()
        filmsListLiveData.postValue(films)
    }
}