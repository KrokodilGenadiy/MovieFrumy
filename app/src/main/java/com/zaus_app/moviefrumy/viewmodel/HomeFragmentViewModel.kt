package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {
    var status: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val showShimmering: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var scope: CoroutineScope
    var isFromInternet: Boolean = false
    var currentPage = 1
    @Inject
    lateinit var interactor: Interactor
    var filmsListData: Flow<List<Film>>


    private val apiCallback = object : ApiCallback {
        override fun onSuccess() {
            isFromInternet = true
            showShimmering.postValue(false)
            filmsListData = interactor.getFilmsFromDB()
        }

        override fun onFailure() {
            showShimmering.postValue(false)
            scope.launch {
                filmsListData = interactor.getFilmsFromDB()
                status.postValue(true)
            }
        }
    }

    fun getFilms() {
        showShimmering.postValue(true)
        interactor.getFilmsFromApi(1, apiCallback)
    }

    init {
        App.instance.dagger.inject(this)
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun doPagination(visibleItemCount: Int,totalItemCount: Int,pastVisibleItemCount: Int) {
        if ((visibleItemCount+pastVisibleItemCount) >= totalItemCount -2) {
            val page = ++currentPage
            interactor.getFilmsFromApi(page,apiCallback)
        }
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}


