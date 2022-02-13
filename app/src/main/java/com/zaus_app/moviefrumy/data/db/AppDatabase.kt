package com.zaus_app.moviefrumy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaus_app.moviefrumy.data.dao.FilmDao
import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.utils.RoomConverter

@Database(entities = [Film::class, FavoriteFilm::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}