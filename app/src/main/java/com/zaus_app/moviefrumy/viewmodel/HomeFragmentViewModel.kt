package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Film
import com.zaus_app.moviefrumy.domain.Interactor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeFragmentViewModel : ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    private val interactor: Interactor by inject()
    private var currentPage = 1
    //Инициализируем интерактор

    val apiCallback = object : ApiCallback {
        override fun onSuccess(films: List<Film>) {
            filmsListLiveData.postValue(films)
        }

        override fun onFailure() {
        }
    }

    init {
        interactor.getFilmsFromApi(2, apiCallback)
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