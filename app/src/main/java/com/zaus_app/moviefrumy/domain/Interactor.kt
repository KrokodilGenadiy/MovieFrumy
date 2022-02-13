package com.zaus_app.moviefrumy.domain

import androidx.lifecycle.LiveData
import com.zaus_app.moviefrumy.data.*
import com.zaus_app.moviefrumy.data.entity.FavoriteFilm
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.data.entity.TmdbResults
import com.zaus_app.moviefrumy.utils.Converter
import com.zaus_app.moviefrumy.utils.PreferenceProvider
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, getDefaultLanguageFromPreferences(), page).enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                scope.launch {
                    repo.deleteAll()
                    repo.putToDb(list)
                }
                callback.onSuccess()
            }
            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    fun nukeMainTable() {
        scope.launch {
            repo.deleteAll()
        }
    }
    fun getFavoriteFilms(): Flow<List<FavoriteFilm>> = repo.getAllFromFavorites()
    fun addToFavorites(film: Film) {
        scope.launch {
            repo.addToFavorites(film)
        }
    }
    fun removeFromFavorites(film: Film) {
        scope.launch {
            repo.removeFromFavorites(film)
        }
    }

    fun saveDefaultCategoryToPreferences(category: String) = preferences.saveDefaultCategory(category)
    fun saveDefaultLanguageToPreferences(language: String) = preferences.saveDefaultLanguage(language)

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()
    fun getDefaultLanguageFromPreferences() = preferences.getDefaultLanguage()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()
}