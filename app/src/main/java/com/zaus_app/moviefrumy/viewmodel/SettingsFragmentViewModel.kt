package com.zaus_app.moviefrumy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    //Инжектим интерактор
    @Inject
    lateinit var interactor: Interactor
    val languagePropertyLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        //Получаем категорию при инициализации, чтобы у нас сразу подтягивалась категория
        getLanguageProperty()
    }

    private fun getLanguageProperty() {
        languagePropertyLiveData.value = interactor.getDefaultLanguageFromPreferences()
    }

    fun putLanguageProperty(language: String) {
        //Сохраняем в настройки
        interactor.saveDefaultLanguageToPreferences(language)
        //И сразу забираем, чтобы сохранить состояние в модели
        getLanguageProperty()
    }
}