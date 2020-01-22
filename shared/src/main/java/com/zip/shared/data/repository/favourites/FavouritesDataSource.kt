package com.zip.shared.data.repository.favourites

import androidx.lifecycle.LiveData
import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.database.stockList.StockListEntity
import javax.inject.Inject

/**
 * [FavouritesDataSource] is local data source to update favourites for user in database
 */
class FavouritesDataSource @Inject constructor(private val stockListDao: StockListDao) {

    val favouriteStocks: LiveData<List<StockListEntity>> = stockListDao.favouriteStocks()

    suspend fun favouriteSymbols(): List<String> = stockListDao.favouriteSymbols()

    suspend fun updateFavouriteStatus(symbol: String): StockListEntity? {
        val company = stockListDao.companyFromSymbol(symbol)
        company?.let {
            val stockListEntity = StockListEntity(
                it.symbol,
                it.name,
                it.price,
                it.changePercent,
                it.image,
                it.isFavourite.not())
            if (stockListDao.updateStockListEntity(stockListEntity) > 0) {
                return stockListEntity
            }
        }
        return null
    }

}