package com.zaus_app.moviefrumy.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.*


class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val default_language = Locale.getDefault().language
    private val preference: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        if(preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY)
                              putString(KEY_DEFAULT_LANGUAGE, default_language) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    fun saveDefaultCategory(category: String) {
        preference.edit { putString(KEY_DEFAULT_CATEGORY, category)
        }
    }
    fun getDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }


    fun getDefaultLanguage(): String {
        return preference.getString(KEY_DEFAULT_LANGUAGE, default_language) ?: default_language
    }

    fun saveDefaultLanguage(language: String){
        preference.edit { putString(KEY_DEFAULT_LANGUAGE,language) }
    }

    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"
        private const val KEY_DEFAULT_LANGUAGE = "default_language"
    }
}