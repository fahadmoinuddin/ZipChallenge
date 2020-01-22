package com.zip.challenge.home.settings

import androidx.lifecycle.ViewModel
import com.zip.shared.di.FragmentScoped
import com.zip.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module which is used to inject [SettingsViewModel] through dependency injection
 */
@Module
abstract class SettingsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

}