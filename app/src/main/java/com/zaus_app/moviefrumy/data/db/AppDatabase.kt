package com.zaus_app.moviefrumy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaus_app.moviefrumy.data.dao.FilmDao
import com.zaus_app.moviefrumy.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}