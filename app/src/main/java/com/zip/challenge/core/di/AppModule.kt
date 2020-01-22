package com.zip.challenge.core.di

import android.content.Context
import com.zip.challenge.MyApplication
import com.zip.shared.data.database.ZipDatabase
import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.prefs.PreferenceStorage
import com.zip.shared.data.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MyApplication) = application.applicationContext

    @Provides
    @Singleton
    fun providePreferenceStorage(context: Context): PreferenceStorage = SharedPreferenceStorage(context)

    @Singleton
    @Provides
    fun providesZipDatabase(context: Context): ZipDatabase = ZipDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideStockListDao(zipDatabase: ZipDatabase): StockListDao = zipDatabase.stockListDao()

}