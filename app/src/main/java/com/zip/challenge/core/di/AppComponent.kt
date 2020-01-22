package com.zip.challenge.core.di

import com.zip.challenge.MyApplication
import com.zip.shared.di.NetworkModule
import com.zip.shared.di.ServiceBindingModule
import com.zip.shared.di.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    ServiceBindingModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}