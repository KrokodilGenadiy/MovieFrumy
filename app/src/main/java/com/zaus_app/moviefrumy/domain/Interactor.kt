package com.zaus_app.moviefrumy.domain

import com.zaus_app.moviefrumy.data.API
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.TmdbApi
import com.zaus_app.moviefrumy.data.entity.TmdbResults
import com.zaus_app.moviefrumy.utils.Converter
import com.zaus_app.moviefrumy.utils.PreferenceProvider
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, getDefaultLanguageFromPreferences(), page).enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(Converter.convertApiListToDTOList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
    fun getFavoriteFilms(): MutableList<Film> = repo.favoritesList
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    fun saveDefaultLanguageToPreferences(language: String) {
        preferences.saveDefaultLanguage(language)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()
    fun getDefaultLanguageFromPreferences() = preferences.getDefaultLanguage()
}