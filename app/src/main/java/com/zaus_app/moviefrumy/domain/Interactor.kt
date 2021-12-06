package com.zaus_app.moviefrumy.domain

import com.zaus_app.moviefrumy.data.MainRepository

class Interactor(val repo: MainRepository): BaseInteractor {
    override fun getFilmsDB() = repo.filmsDataBase
    override fun getFavoriteFilms(): MutableList<Film> = repo.favoritesList
}