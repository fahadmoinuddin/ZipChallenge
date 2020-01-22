package com.zip.challenge.core.di

import com.zip.challenge.companyProfile.CompanyProfileActivity
import com.zip.challenge.companyProfile.CompanyProfileModule
import com.zip.challenge.home.HomeActivity
import com.zip.challenge.home.favoruites.FavouritesModule
import com.zip.challenge.home.settings.SettingsModule
import com.zip.challenge.home.stockList.StockListModule
import com.zip.shared.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [StockListModule::class, FavouritesModule::class, SettingsModule::class])
    abstract fun HomeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [CompanyProfileModule::class])
    abstract fun CompanyProfileActivity(): CompanyProfileActivity

}