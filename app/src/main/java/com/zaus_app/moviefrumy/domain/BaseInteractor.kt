package com.zaus_app.moviefrumy.domain

interface BaseInteractor {
    fun getFilmsDB(): List<Film>
    fun getFavoriteFilms(): MutableList<Film>
}