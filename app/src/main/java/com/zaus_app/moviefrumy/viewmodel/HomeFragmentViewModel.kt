package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.domain.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {
    var filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    //Инициализируем интерактор
    private var currentPage = 1
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    private val apiCallback = object : ApiCallback {
        override fun onSuccess(films: List<Film>) {
            showProgressBar.postValue(false)
            filmsListLiveData.postValue(films)
        }

        override fun onFailure() {
            Executors.newSingleThreadExecutor().execute {
                showProgressBar.postValue(false)
                filmsListLiveData = interactor.getFilmsFromDB() as MutableLiveData<List<Film>>
            }
        }
    }

    fun getFilms() {
        showProgressBar.postValue(true)
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


