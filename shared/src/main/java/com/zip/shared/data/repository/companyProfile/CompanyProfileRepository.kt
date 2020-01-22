package com.zip.shared.data.repository.companyProfile

import com.zip.shared.api.result
import com.zip.shared.data.database.stockList.StockListEntity
import javax.inject.Inject

/**
 * [CompanyProfileRepository] communicates with [CompanyProfileRemoteDataSource] and [CompanyProfileDataSource]
 */
class CompanyProfileRepository @Inject constructor(private val companyProfileRemoteDataSource: CompanyProfileRemoteDataSource,
                                                   private val companyProfileDataSource: CompanyProfileDataSource) {

    fun companyBySymbol(symbol: String) = companyProfileDataSource.companyBySymbol(symbol)

    //fetch company profile from backend
    suspend fun companyProfile(symbolList: String) =
        result { companyProfileRemoteDataSource.companyProfile(symbolList) }

    //fetch historical price for the company with the date range
    suspend fun historicalPrice(symbol: String, fromDate: String, toDate: String) =
        result { companyProfileRemoteDataSource.historicalPrice(symbol, fromDate, toDate) }

    //insert the companies in local database
    suspend fun insertStocks(list: List<StockListEntity>) {
        companyProfileDataSource.insertStocks(list)
    }

    suspend fun updateUsingSymbolList(list: List<StockListEntity>?) {
        list?.forEach { company ->
            companyProfileDataSource.updateUsingSymbol(company)
        }
    }
}