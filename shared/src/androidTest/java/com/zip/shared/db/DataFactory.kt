package com.zip.shared.db

import com.zip.model.companyProfile.CompanyProfileResponse
import com.zip.model.stockList.StockListResponse

object DataFactory {

    fun getStockList(): StockListResponse = StockListResponse(TestData.symbolsList)

    fun getSPYCompanyStock(): CompanyProfileResponse = CompanyProfileResponse(TestData.companyProfileList)
}