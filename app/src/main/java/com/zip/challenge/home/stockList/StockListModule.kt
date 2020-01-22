package com.zip.challenge.home.stockList

import androidx.lifecycle.ViewModel
import com.zip.shared.di.FragmentScoped
import com.zip.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module which is used to inject [StockListViewModel] through dependency injection
 */
@Module
abstract class StockListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeStockListFragment(): StockListFragment

    @Binds
    @IntoMap
    @ViewModelKey(StockListViewModel::class)
    abstract fun bindStockListViewModel(viewModel: StockListViewModel): ViewModel

}