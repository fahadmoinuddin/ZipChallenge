package com.zip.shared.data.repository.realtimePrice

import com.zip.model.realtimePrice.CompanyPrice
import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.database.stockList.StockListEntity
import javax.inject.Inject

/**
 * [RealtimePriceDataSource] is local data source to update real time price in database
 */
class RealtimePriceDataSource @Inject constructor(private val stockListDao: StockListDao) {

    suspend fun updateRealtimePrice(companyPrice: CompanyPrice) {
        val company = stockListDao.companyFromSymbol(companyPrice.symbol ?: "")
        company?.let {
            val stockListEntity = StockListEntity(
                it.symbol,
                it.name,
                companyPrice.price ?: it.price,
                it.changePercent,
                it.image,
                it.isFavourite)
            stockListDao.updateStockListEntity(stockListEntity)
        }
    }
}