package com.zaus_app.moviefrumy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    //Main List
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): Flow<List<Film>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

    @Query("DELETE FROM cached_films")
    fun nukeTable()

    //Favorites List
    @Query("SELECT * FROM favorite_films")
    fun getFavoriteFilms(): Flow<List<FavoriteFilm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(film: FavoriteFilm)

    @Delete
    fun removeFromFavorites(film: FavoriteFilm)
}