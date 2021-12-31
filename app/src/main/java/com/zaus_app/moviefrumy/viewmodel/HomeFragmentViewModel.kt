package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.domain.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    private var currentPage = 1
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    private val apiCallback = object : ApiCallback {
        override fun onSuccess(films: List<Film>) {
            filmsListLiveData.postValue(films)
        }

        override fun onFailure() {
            Executors.newSingleThreadExecutor().execute {
                filmsListLiveData.postValue(interactor.getFilmsFromDB())
            }
        }
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1, apiCallback)
    }

    init {
        App.instance.dagger.inject(this)
        getFilms()
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

    fun doPagination(visibleItemCount: Int,totalItemCount: Int,pastVisibleItemCOunt: Int) {
        if ((visibleItemCount+pastVisibleItemCOunt) >= totalItemCount -2) {
            val page = ++currentPage
            interactor.getFilmsFromApi(page,apiCallback)
        }
    }
}


