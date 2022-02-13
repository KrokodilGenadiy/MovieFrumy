package com.zaus_app.moviefrumy.data

import androidx.lifecycle.LiveData
import com.zaus_app.moviefrumy.data.dao.FilmDao
import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.utils.Converter
import kotlinx.coroutines.flow.Flow

class MainRepository(private val filmDao: FilmDao) {
    //Main list
    fun putToDb(films: List<Film>) = filmDao.insertAll(films)
    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()
    fun deleteAll() = filmDao.nukeTable()

    //Favorites list
    fun addToFavorites(film: Film) = filmDao.addToFavorites(Converter.convertFilmToFavorite(film))
    fun removeFromFavorites(film: Film) = filmDao.removeFromFavorites(Converter.convertFilmToFavorite(film))
    fun getAllFromFavorites(): Flow<List<FavoriteFilm>> = filmDao.getFavoriteFilms()
}