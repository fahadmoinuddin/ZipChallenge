package com.zip.shared.data.repository.stockList

import com.zip.model.stockList.StockListResponse
import com.zip.shared.api.BaseRemoteDataSource
import com.zip.shared.api.NetworkService
import com.zip.shared.api.Result
import javax.inject.Inject

/**
 * [StockListRemoteDataSource] is remote data source to fetch all stocks from backend
 */
class StockListRemoteDataSource @Inject constructor(private val service: NetworkService) : BaseRemoteDataSource() {

    suspend fun stockList(): Result<StockListResponse> =
        getResult { service.stockList() }
}