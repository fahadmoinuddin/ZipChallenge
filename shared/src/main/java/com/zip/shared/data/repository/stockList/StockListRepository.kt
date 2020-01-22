package com.zip.shared.data.repository.stockList

import com.zip.model.stockList.StockListResponse
import com.zip.shared.api.Result
import com.zip.shared.api.result
import javax.inject.Inject

/**
 * [StockListRepository] communicates with [StockListDataSource] and [StockListRemoteDataSource]
 */
class StockListRepository @Inject constructor(private val stockListRemoteDataSource: StockListRemoteDataSource,
                                              stockListDataSource: StockListDataSource) {

    val allStocks = stockListDataSource.allStocks

    suspend fun stockList(): Result<StockListResponse> =
        result {
            stockListRemoteDataSource.stockList()
        }
}