package com.zaus_app.moviefrumy.domain

interface BaseInteractor {
    fun getFavoriteFilms(): MutableList<Film>
}