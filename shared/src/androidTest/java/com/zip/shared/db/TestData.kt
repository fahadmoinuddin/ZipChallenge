package com.zip.shared.db

import com.zip.model.companyProfile.CompanyProfileMain
import com.zip.model.companyProfile.Profile
import com.zip.model.realtimePrice.CompanyPrice
import com.zip.model.stockList.Symbol

object TestData {
    val symbolsList: List<Symbol> = listOf(
        Symbol("SPY", "SPDR S&P 500", 331.95),
        Symbol("CMCSA", "Comcast Corporation Class A Common Stock", 47.5),
        Symbol("KMI", "Kinder Morgan Inc.", 21.36),
        Symbol("INTC", "Intel Corporation", 59.6),
        Symbol("MU", "Micron Technology Inc.", 57.66)
    )

    val companyProfileList: List<CompanyProfileMain> = listOf(
        CompanyProfileMain(
            "SPY",
            Profile(
                332.92,
                "3.0465776E11",
                "5.0668054",
                1.03,
                "(+0.31%)",
                "SPDR S&P 500",
                "",
                "",
                "https://financialmodelingprep.com/images-New-jpg/SPY.jpg"))
    )

    val realTimePrice: CompanyPrice = CompanyPrice("SPY", 332.12)
}