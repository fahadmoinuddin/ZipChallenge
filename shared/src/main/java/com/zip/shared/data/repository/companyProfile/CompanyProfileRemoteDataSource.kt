package com.zip.shared.data.repository.companyProfile

import com.zip.model.companyProfile.CompanyProfileResponse
import com.zip.model.historicalPrice.HistoricalPriceResponse
import com.zip.shared.api.BaseRemoteDataSource
import com.zip.shared.api.NetworkService
import com.zip.shared.api.Result
import javax.inject.Inject

/**
 * [CompanyProfileRemoteDataSource] is remote data source to fetch company profile from backend
 */
class CompanyProfileRemoteDataSource @Inject constructor(private val service: NetworkService) : BaseRemoteDataSource() {

    //fetch company profile from backend
    suspend fun companyProfile(symbolList: String): Result<CompanyProfileResponse> =
        getResult { service.companyProfile(symbolList) }

    //fetch historical price from backend
    suspend fun historicalPrice(symbol: String, fromDate: String, toDate: String): Result<HistoricalPriceResponse> =
        getResult { service.historicalPrice(symbol, fromDate, toDate) }
}