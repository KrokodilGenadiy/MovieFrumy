package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Film
import com.zaus_app.moviefrumy.domain.Interactor
import javax.inject.Inject


class FavoriteFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.injectFavorites(this)
        val films = interactor.getFavoriteFilms()
        filmsListLiveData.postValue(films)
    }
}