package com.zip.challenge.home.favoruites

import androidx.lifecycle.ViewModel
import com.zip.shared.di.FragmentScoped
import com.zip.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module which is used to inject [FavouritesViewModel] through dependency injection
 */
@Module
abstract class FavouritesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeFavouritesFragment(): FavouritesFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindFavouritesViewModel(viewModel: FavouritesViewModel): ViewModel

}