package com.zaus_app.moviefrumy.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_films", indices = [Index(value = ["title"], unique = true)])
data class FavoriteFilm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster: String?,
    @ColumnInfo(name = "overview") val description: String,
    @ColumnInfo(name = "genres") val genres: List<Int>,
    @ColumnInfo(name = "vote_average") var rating: Double = 0.0,
    var isInFavorites: Boolean = false
) : Parcelable