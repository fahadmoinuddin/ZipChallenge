package com.zip.shared.data.repository.stockList

import androidx.lifecycle.LiveData
import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.database.stockList.StockListEntity
import javax.inject.Inject

/**
 * [StockListDataSource] is local data source to fetch all stocks from database
 */
class StockListDataSource @Inject constructor(stockListDao: StockListDao) {

    val allStocks: LiveData<List<StockListEntity>> = stockListDao.stockList()

}