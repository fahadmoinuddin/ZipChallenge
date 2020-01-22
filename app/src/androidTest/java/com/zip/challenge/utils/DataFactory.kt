package com.zip.challenge.utils

import com.zip.model.stockList.StockListResponse

object DataFactory {

    fun getStockList(): StockListResponse = StockListResponse(TestData.symbolsList)
}