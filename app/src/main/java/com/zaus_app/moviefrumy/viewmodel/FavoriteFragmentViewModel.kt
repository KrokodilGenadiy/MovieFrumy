package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.domain.Interactor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteFragmentViewModel : ViewModel() {
    var filmsListData: Flow<List<FavoriteFilm>>
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        filmsListData = interactor.getFavoriteFilms()
    }
}