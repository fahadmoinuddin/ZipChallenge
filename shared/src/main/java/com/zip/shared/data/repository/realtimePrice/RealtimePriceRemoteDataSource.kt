package com.zip.shared.data.repository.realtimePrice

import com.zip.model.realtimePrice.RealtimePriceResponse
import com.zip.shared.api.BaseRemoteDataSource
import com.zip.shared.api.NetworkService
import com.zip.shared.api.Result
import javax.inject.Inject

/**
 * [RealtimePriceRemoteDataSource] is remote data source to fetch real time price of companies from backend
 */
class RealtimePriceRemoteDataSource @Inject constructor(private val service: NetworkService) : BaseRemoteDataSource() {

    suspend fun stockList(symbolList: String): Result<RealtimePriceResponse> =
        getResult { service.realtimePrice(symbolList) }
}