package com.zip.model.historicalPrice

import java.util.*

data class HistoricalPriceResponse(
    val symbol: String?,
    val historical: List<HistoricalPrice>?
)

data class HistoricalPrice(
    val date: Date?,
    val close: Double?,
    val label: String?
)