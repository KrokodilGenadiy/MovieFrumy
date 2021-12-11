package com.zaus_app.moviefrumy.domain

import com.zaus_app.moviefrumy.data.API
import com.zaus_app.moviefrumy.data.MainRepository
import com.zaus_app.moviefrumy.data.TmdbApi
import com.zaus_app.moviefrumy.data.entity.TmdbResults
import com.zaus_app.moviefrumy.utils.Converter
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi): BaseInteractor {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResults> {
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
    override fun getFavoriteFilms(): MutableList<Film> = repo.favoritesList
}