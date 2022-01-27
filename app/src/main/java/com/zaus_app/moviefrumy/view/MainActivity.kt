package com.zaus_app.moviefrumy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.databinding.ActivityMainBinding
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.view.fragments.*
import java.util.Locale
import android.content.Intent
import android.content.res.Configuration
import com.zaus_app.moviefrumy.domain.Interactor
import com.zaus_app.moviefrumy.utils.PreferenceProvider
import javax.inject.Inject
import android.app.Activity
import android.content.Context

import android.content.SharedPreferences
import android.preference.PreferenceManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

        //Зупускаем фрагмент при старте
        changeFragment(HomeFragment(), "home")
    }

    fun launchDetailsFragment(film: Film) {
        //Создаем "посылку"
        val bundle = Bundle()
        //Кладем наш фильм в "посылку"
        bundle.putParcelable("film", film)
        //Кладем фрагмент с деталями в перменную
        val fragment = checkFragmentExistence("details") ?: DetailsFragment()
        //Прикрепляем нашу "посылку" к фрагменту
        fragment.arguments = bundle

        //Запускаем фрагмент
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, "details")
            .addToBackStack("details")
            .commit()
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нвого фрагмента
                    changeFragment(fragment ?: HomeFragment(), tag)

                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    //Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .commit()
    }

    // TODO: 17.01.2022  Better remove the language change completely or find a stable solution
    fun setLocale(lang: String) {
        changeLang(lang)
        val refresh = Intent(this, MainActivity::class.java)
        finish()
        startActivity(refresh)
    }

    fun loadLocale() {
        val preferences = getSharedPreferences("settings", MODE_PRIVATE)
        val languagePref = "default_language"
        val language = preferences.getString(languagePref, "")
        changeLang(language!!)
    }

    fun changeLang(lang: String) {
        if (lang.equals("", ignoreCase = true)) return
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics())
    }


}