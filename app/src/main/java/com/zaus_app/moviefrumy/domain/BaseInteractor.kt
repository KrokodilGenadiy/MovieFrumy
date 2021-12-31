package com.zaus_app.moviefrumy.domain

import com.zaus_app.moviefrumy.data.entity.Film

interface BaseInteractor {
    fun getFavoriteFilms(): MutableList<Film>
}