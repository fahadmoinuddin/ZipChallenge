package com.zip.shared.data.repository.companyProfile

import com.zip.shared.util.extensions.DateFormat
import java.util.*

//Helper methods for historical data options to display line chart
interface HistoricalOptionOperations {
    fun historicalDateSubtractComponent(): Int //component to generate the from date
    fun dateFormatForChart(): DateFormat //date format for x-axis label
    fun calendarOptionForChart(): Int //calendar option to group historical data with
}

enum class HistoricalOption : HistoricalOptionOperations {
    Months_3 {
        override fun historicalDateSubtractComponent(): Int = -3
        override fun dateFormatForChart(): DateFormat = DateFormat.MMM_yy_w
        override fun calendarOptionForChart(): Int = Calendar.WEEK_OF_YEAR
    },
    Months_6 {
        override fun historicalDateSubtractComponent(): Int = -6
        override fun dateFormatForChart(): DateFormat = DateFormat.MMM_yy_w
        override fun calendarOptionForChart(): Int = Calendar.WEEK_OF_YEAR
    },
    Year_1 {
        override fun historicalDateSubtractComponent(): Int = -12
        override fun dateFormatForChart(): DateFormat = DateFormat.MMM_yy
        override fun calendarOptionForChart(): Int = Calendar.MONTH
    },
    Year_3 {
        override fun historicalDateSubtractComponent(): Int = -36
        override fun dateFormatForChart(): DateFormat = DateFormat.MMM_yy
        override fun calendarOptionForChart(): Int = Calendar.MONTH
    },
    Unknown {
        override fun historicalDateSubtractComponent(): Int = 0
        override fun dateFormatForChart(): DateFormat = DateFormat.MMM_yy_w
        override fun calendarOptionForChart(): Int = Calendar.WEEK_OF_YEAR
    };

}