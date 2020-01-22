package com.zip.shared.util

object Constants {
    const val GENERIC_ERROR = "Something went wrong. Please try again."

    //Endpoints Path
    const val COMPANY_SYMBOL = "symbol"
    const val COMPANY_SYMBOL_LIST = "symbolList"

    //Endpoints Query Params
    const val FROM = "from"
    const val TO = "to"

    //Services tag
    const val REAL_TIME_PRICE_SERVICE = "com.zip.refresh_realtime_price_service"

    //Intent extras
    const val EXTRA_SYMBOL = "symbol"

    const val REFRESH_REAL_TIME_PRICE_INTERVAL = 15000L
}