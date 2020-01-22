package com.zip.shared.data.repository.realtimePrice

import com.zip.model.realtimePrice.CompanyPrice
import com.zip.shared.api.Result
import com.zip.shared.api.succeeded
import com.zip.shared.util.Constants
import javax.inject.Inject

/**
 * [RealtimePriceRepository] communicates with [RealtimePriceDataSource] and [RealtimePriceRemoteDataSource]
 */
class RealtimePriceRepository @Inject constructor(
    private val realtimePriceRemoteDataSource: RealtimePriceRemoteDataSource,
    private val realtimePriceDataSource: RealtimePriceDataSource
) {

    //fetch real time price from backend
    suspend fun realtimePrice(symbolList: String): Result<Boolean> {
        val result = realtimePriceRemoteDataSource.stockList(symbolList)
        return when (result) {
            is Result.Success -> {
                if (result.succeeded) {
                    updateRealtimePrice(result.data.companiesPriceList)
                    Result.Success(true)
                } else {
                    Result.Error(Constants.GENERIC_ERROR)
                }
            }
            else -> result as Result.Error
        }
    }

    //update real time price in local database
    private suspend fun updateRealtimePrice(list: List<CompanyPrice>?) {
        list?.forEach {
            realtimePriceDataSource.updateRealtimePrice(it)
        }
    }
}