package com.zip.shared.services

import android.content.Intent
import com.zip.shared.data.repository.favourites.FavouritesRepository
import com.zip.shared.data.repository.realtimePrice.RealtimePriceRepository
import com.zip.shared.util.Constants
import dagger.android.DaggerIntentService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RefreshRealtimePriceService] is a background service that fetches the real time price of symbols.
 * Once it fetches the price, it is saved in room database and published to all the listeners in the app.
 */
class RefreshRealtimePriceService : DaggerIntentService(Constants.REAL_TIME_PRICE_SERVICE) {

    @Inject
    lateinit var realtimePriceRepository: RealtimePriceRepository

    @Inject
    lateinit var favouritesRepository: FavouritesRepository

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onHandleIntent(intent: Intent?) {
        ioScope.launch {
            if (intent?.hasExtra(Constants.EXTRA_SYMBOL) == true) {
                // This service was started from CompayProfile screen
                realtimePrice(intent.getStringExtra(Constants.EXTRA_SYMBOL) ?: "")
            } else {
                // This service is to refresh the favourite symbols
                val favouriteSymbols =
                    favouritesRepository.favouriteSymbols() //Get the favourite symbols
                if (favouriteSymbols.isNotEmpty()) {
                    //Convert to list
                    val favouriteSymbolsAsString = favouriteSymbols.joinToString(separator = ",")
                    realtimePrice(favouriteSymbolsAsString)
                }
            }
        }
    }

    //fetch the real time price
    private suspend fun realtimePrice(symbolsList: String) {
        realtimePriceRepository.realtimePrice(symbolsList)
    }
}