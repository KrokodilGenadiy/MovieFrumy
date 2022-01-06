package com.zaus_app.moviefrumy.di

import com.zaus_app.moviefrumy.di.modules.DatabaseModule
import com.zaus_app.moviefrumy.di.modules.DomainModule
import com.zaus_app.moviefrumy.di.modules.RemoteModule
import com.zaus_app.moviefrumy.view.fragments.DetailsFragment
import com.zaus_app.moviefrumy.view.fragments.FavoritesFragment
import com.zaus_app.moviefrumy.viewmodel.*
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
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(favoritesFragmentViewModel: FavoriteFragmentViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
    fun inject(bottomSheetFragmentViewModel: BottomSheetFragmentViewModel)
    fun inject(detailsFragmentViewModel: DetailsFragmentViewModel)
}