package com.zip.challenge

import android.content.Intent
import android.os.Handler
import com.zip.challenge.core.di.DaggerAppComponent
import com.zip.shared.data.prefs.SharedPreferenceStorage
import com.zip.shared.services.RefreshRealtimePriceService
import com.zip.shared.util.Constants
import com.zip.shared.util.ThemeHelper
import com.zip.shared.util.ThemeMode
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApplication : DaggerApplication() {

    @Inject
    lateinit var preferenceStorage: SharedPreferenceStorage

    //Handler to start a runnable every 15 seconds
    private val handler: Handler = Handler()

    /**
    This runnable starts [RefreshRealtimePriceService] to fetch the real time price
     */
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            startService(Intent(applicationContext, RefreshRealtimePriceService::class.java))
            handler.postDelayed(this, Constants.REFRESH_REAL_TIME_PRICE_INTERVAL)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        //start the handler
        handler.postDelayed(runnable, 0)

        //Apply theme on start up based on user selected
        ThemeHelper.applyTheme(if (preferenceStorage.isDarkMode) ThemeMode.Dark else ThemeMode.Light)
    }
}