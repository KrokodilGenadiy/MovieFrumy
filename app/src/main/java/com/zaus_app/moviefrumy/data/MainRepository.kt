package com.zaus_app.moviefrumy.data

import androidx.lifecycle.LiveData
import com.zaus_app.moviefrumy.data.dao.FilmDao
import com.zaus_app.moviefrumy.data.entity.Film

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()

    fun deleteAll() = filmDao.nukeTable()
}