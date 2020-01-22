package com.zip.model.stockList

data class StockListResponse(val symbolsList: List<Symbol>?)

data class Symbol(
    val symbol: String?,
    val name: String?,
    val price: Double?
)