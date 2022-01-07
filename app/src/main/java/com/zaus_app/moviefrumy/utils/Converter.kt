package com.zaus_app.moviefrumy.utils

import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.data.entity.TmdbFilm

object Converter {
    fun convertApiListToDTOList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(
                Film(
                    title = it.title,
                    poster = it.posterPath,
                    description = it.overview,
                    genres = it.genreIds,
                    rating = it.voteAverage,
                    isInFavorites = false
                )
            )
        }
        return result
    }
}