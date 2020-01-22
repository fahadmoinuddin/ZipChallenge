package com.zip.shared.api

import com.zip.shared.util.Constants

//List of service calls endpoints
object EndPoints {

    const val STOCK_LIST = "company/stock/list"
    const val COMPANY_HISTORICAL_PRICE = "historical-price-full/{${Constants.COMPANY_SYMBOL}}"
    const val COMPANY_PROFILE = "company/profile/{${Constants.COMPANY_SYMBOL_LIST}}"
    const val COMPANY_REAL_TIME_PRICE = "stock/real-time-price/{${Constants.COMPANY_SYMBOL_LIST}},"

}