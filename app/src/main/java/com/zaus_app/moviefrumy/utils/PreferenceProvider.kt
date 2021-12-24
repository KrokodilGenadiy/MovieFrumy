package com.zaus_app.moviefrumy.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.*

class PreferenceProvider(context: Context) {
    //Нам нужен контекст приложения
    private val appContext = context.applicationContext
    //Создаем экземпляр SharedPreferences
    private val preference: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        //Логика для первого запуска приложения, чтобы положить наши настройки,
        //Сюда потом можно добавить и другие настройки
        if(preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY)
                              putString(KEY_DEFAULT_LANGUAGE,DEFAULT_LANGUAGE) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    //Category prefs
    //Сохраняем категорию
    fun saveDefaultCategory(category: String) {
        preference.edit { putString(KEY_DEFAULT_CATEGORY, category)
        }
    }
    //Забираем категорию
    fun getDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }


    fun getDefaultLanguage(): String {
        return preference.getString(KEY_DEFAULT_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    fun saveDefaultLanguage(language: String){
        preference.edit { putString(KEY_DEFAULT_LANGUAGE,language) }
    }


    //Ключи для наших настроек, по ним мы их будем получать
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"

        private const val KEY_DEFAULT_LANGUAGE = "default_language"
        private const val DEFAULT_LANGUAGE = "ru-RU"
    }
}