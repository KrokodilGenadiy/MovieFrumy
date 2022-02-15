package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {
    var status: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val showShimmering: Channel<Boolean>
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var isFromInternet: Boolean = false
    var currentPage = 1
    var filmsListData: Flow<List<Film>>
    @Inject
    lateinit var interactor: Interactor


    private val apiCallback = object : ApiCallback {
        override fun onSuccess() {
            isFromInternet = true
            filmsListData = interactor.getFilmsFromDB()
        }

        override fun onFailure() {
            scope.launch {
                filmsListData = interactor.getFilmsFromDB()
                status.postValue(true)
            }
        }
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1, apiCallback)
    }

    init {
        App.instance.dagger.inject(this)
        showShimmering = interactor.shimmeringState
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


