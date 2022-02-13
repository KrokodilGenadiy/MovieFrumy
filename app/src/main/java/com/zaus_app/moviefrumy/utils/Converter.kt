package com.zaus_app.moviefrumy.utils

import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.data.entity.TmdbFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList

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

        fun convertListOfFavoriteToFilmList(list: List<FavoriteFilm>): MutableList<Film> {
        val result = mutableListOf<Film>()
        list.forEach {
            result.add(
                Film(
                    id = it.id,
                    title = it.title,
                    poster = it.poster,
                    description = it.description,
                    genres = it.genres,
                    rating = it.rating,
                    isInFavorites = true
                )
            )
        }
        return result
    }

    fun convertFilmToFavorite(film: Film): FavoriteFilm = FavoriteFilm(
        film.id,
        film.title,
        film.poster,
        film.description,
        film.genres,
        film.rating,
        true
    )
}