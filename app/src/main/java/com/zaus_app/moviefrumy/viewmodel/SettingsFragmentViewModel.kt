package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor
    val languagePropertyLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        getLanguageProperty()
    }

    private fun getLanguageProperty() {
        languagePropertyLiveData.value = interactor.getDefaultLanguageFromPreferences()
    }

    fun putLanguageProperty(language: String) {
        interactor.saveDefaultLanguageToPreferences(language)
        getLanguageProperty()
    }
}