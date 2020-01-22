package com.zip.shared.data.repository

import androidx.lifecycle.LiveData
import com.zip.shared.api.Result
import com.zip.shared.api.result
import com.zip.shared.api.succeeded
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.data.prefs.PreferenceStorage
import com.zip.shared.data.repository.companyProfile.CompanyProfileRepository
import com.zip.shared.data.repository.favourites.FavouritesRepository
import com.zip.shared.data.repository.stockList.StockListRepository
import com.zip.shared.util.Constants
import com.zip.shared.util.extensions.asStockListForView
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * [BaseStocksRepository] is the main repository which handles stocks, their profiles for StockList screen.
 * @param stockListRepository - manages all stocks data
 * @param companyProfileRepository - manages profiles of companies
 * @param favouritesRepository - manages user favourites
 * @param preferenceStorage - manager local preference values
 */
class BaseStocksRepository @Inject constructor(private val stockListRepository: StockListRepository,
                                               private val companyProfileRepository: CompanyProfileRepository,
                                               private val favouritesRepository: FavouritesRepository,
                                               private val preferenceStorage: PreferenceStorage) {

    //LiveData from room database
    val allStocks: LiveData<List<StockListEntity>> = stockListRepository.allStocks

    //Fetch all stocks from the backend and store in the local database
    suspend fun stockList(): Result<Boolean> =
        result {
            val result = stockListRepository.stockList()
            when (result) {
                is Result.Success -> {
                    if (result.succeeded) {
                        //Convert data to StockListEntity type to insert in database
                        val customStockList = result.data.asStockListForView()
                        companyProfileRepository.insertStocks(customStockList)

                        //store success state in preference storage
                        preferenceStorage.dataLoadedAt = Date()

                        //Fetch company profiles of first 10 stocks to show in the view
                        fetchCompanyProfiles(customStockList.map { item -> item.symbol }.subList(0, 10))
                        Result.Success(true)
                    } else {
                        //Error scenario
                        Result.Error(Constants.GENERIC_ERROR)
                    }
                }
                else -> result as Result.Error
            }
        }

    //Fetch company profiles in groups of 3 as the api has retrictions
    suspend fun fetchCompanyProfiles(list: List<String>) {
        val size = list.size
        for (i in 0 until size step 3) {
            val to = if ((i+3) <= size) (i+3) else size
            val symbolListString = list.subList(i, to).joinToString(separator = ",")
            companyProfile(symbolListString)
        }
    }

    suspend fun companyProfile(symbolList: String): Result<Boolean> =
        result {
            val result = companyProfileRepository.companyProfile(symbolList)
            when (result) {
                is Result.Success -> {
                    if (result.succeeded) {
                        // update the company profile in the database
                        updateCompanyProfile(result.data.asStockListForView())
                        Result.Success(true)
                    } else {
                        Result.Error(Constants.GENERIC_ERROR)
                    }
                }
                else -> result as Result.Error
            }
        }

    //update row in database
    private suspend fun updateCompanyProfile(list: List<StockListEntity>?) {
        companyProfileRepository.updateUsingSymbolList(list)
    }

    //update favourite status for the symbol
    suspend fun updateFavouriteStatus(symbol: String): Result<StockListEntity> =
        result {
            favouritesRepository.updateFavouriteStatus(symbol)
        }

    fun loadData(): Boolean {
        val lastLoadedDate = preferenceStorage.dataLoadedAt
        val currentDataMillis = Date().time
        return lastLoadedDate?.let {
            val diffInMillis = currentDataMillis - lastLoadedDate.time
            val diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
            diffInDays > 1
        } ?: true
    }
}