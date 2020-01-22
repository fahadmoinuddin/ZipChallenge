package com.zip.shared.data.repository.companyProfile

import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.database.stockList.StockListEntity
import javax.inject.Inject

/**
 * [CompanyProfileDataSource] is local data source to fetch company details from database
 */
class CompanyProfileDataSource @Inject constructor(private val stockListDao: StockListDao) {

    fun companyBySymbol(symbol: String) = stockListDao.companyLiveDataFromSymbol(symbol)

    suspend fun insertStocks(list: List<StockListEntity>) {
        if (list.isNotEmpty()) {
            stockListDao.deleteAll()
            stockListDao.insert(list)
        }
    }

    suspend fun updateUsingSymbol(stockListEntity: StockListEntity) {
        val company = stockListDao.companyFromSymbol(stockListEntity.symbol)
        company?.let {currentData ->
            val newStockListEntity = StockListEntity(
                stockListEntity.symbol,
                stockListEntity.name,
                stockListEntity.price ?: currentData.price,
                stockListEntity.changePercent,
                stockListEntity.image,
                currentData.isFavourite)
            stockListDao.updateStockListEntity(newStockListEntity
            )
        }
    }



}