package com.zaus_app.moviefrumy.di

import com.zaus_app.moviefrumy.di.modules.DatabaseModule
import com.zaus_app.moviefrumy.di.modules.DomainModule
import com.zaus_app.moviefrumy.di.modules.RemoteModule
import com.zaus_app.moviefrumy.viewmodel.FavoriteFragmentViewModel
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import com.zaus_app.moviefrumy.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun injectHome(homeFragmentViewModel: HomeFragmentViewModel)
    fun injectFavorites(favoritesFragmentViewModel: FavoriteFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun injectSettings(settingsFragmentViewModel: SettingsFragmentViewModel)
}