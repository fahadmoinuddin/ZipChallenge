package com.zip.shared.di

import com.zip.shared.services.RefreshRealtimePriceService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract fun bindRealtimePriceService():RefreshRealtimePriceService
}