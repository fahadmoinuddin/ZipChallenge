package com.zip.shared.api

import com.zip.model.companyProfile.CompanyProfileResponse
import com.zip.model.historicalPrice.HistoricalPriceResponse
import com.zip.model.realtimePrice.RealtimePriceResponse
import com.zip.model.stockList.StockListResponse
import com.zip.shared.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface NetworkService {

    // Returns the list of companies
    @Streaming
    @GET(EndPoints.STOCK_LIST)
    suspend fun stockList(): Response<StockListResponse>

    /***
     * Returns the stock details of the company
     * @param symbolList - comma separated list of symbols whose real time price is needed. For ex: AAPL,AMZN
      */
    @GET(EndPoints.COMPANY_PROFILE)
    suspend fun companyProfile(@Path(Constants.COMPANY_SYMBOL_LIST) symbolList: String): Response<CompanyProfileResponse>

    /***
     * Returns the historical stock price for the company within the given dates.
     * @param symbol - company's trading symbol. For ex: AAPL is apple inc's trading symbol
     * @param fromDate - from date with format yyyy-MM-dd
     * @param toDate - to date with format yyyy-MM-dd
     */
    @GET(EndPoints.COMPANY_HISTORICAL_PRICE)
    suspend fun historicalPrice(
        @Path(Constants.COMPANY_SYMBOL) symbol: String,
        @Query(Constants.FROM) fromDate: String,
        @Query(Constants.TO) toDate: String): Response<HistoricalPriceResponse>

    /***
     * Returns the real time price update for the list of companyies requested.
     * @param symbolList - comma separated list of symbols whose real time price is needed. For ex: AAPL,AMZN
     */
    @GET(EndPoints.COMPANY_REAL_TIME_PRICE)
    suspend fun realtimePrice(@Path(Constants.COMPANY_SYMBOL_LIST) symbolList: String): Response<RealtimePriceResponse>

}