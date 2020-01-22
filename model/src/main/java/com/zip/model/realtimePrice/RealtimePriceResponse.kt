package com.zip.model.realtimePrice

data class RealtimePriceResponse(val companiesPriceList: List<CompanyPrice>?)

data class CompanyPrice(
    val symbol: String?,
    val price: Double?
)