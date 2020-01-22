package com.zip.shared.data.repository.favourites

import androidx.lifecycle.LiveData
import com.zip.shared.api.Result
import com.zip.shared.api.result
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.util.Constants
import javax.inject.Inject

/**
 * [FavouritesRepository] communicates with [FavouritesDataSource] to update favourites
 */
class FavouritesRepository @Inject constructor(private val favouritesDataSource: FavouritesDataSource) {

    val favouriteStocks: LiveData<List<StockListEntity>> = favouritesDataSource.favouriteStocks

    suspend fun favouriteSymbols(): List<String> = favouritesDataSource.favouriteSymbols()

    //update favourites in local database
    suspend fun updateFavouriteStatus(symbol: String): Result<StockListEntity> =
        result {
            val result = favouritesDataSource.updateFavouriteStatus(symbol)
            result?.let {
                Result.Success(it)
            } ?: Result.Error(Constants.GENERIC_ERROR)
        }
}