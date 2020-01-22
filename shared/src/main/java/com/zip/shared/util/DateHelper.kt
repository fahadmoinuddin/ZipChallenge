package com.zip.shared.util

import com.zip.shared.data.repository.companyProfile.HistoricalOption
import java.util.*

//Helper class for date operations
object DateHelper {

    //helper function to calculate the from date to fetch historical data
    fun getDateByHistoricalOption(historicalOption: HistoricalOption): Date {
        val subtractComponent = historicalOption.historicalDateSubtractComponent()
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, subtractComponent) // subtract from the current data
        return calendar.time
    }
}