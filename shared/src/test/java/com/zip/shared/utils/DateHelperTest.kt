package com.zip.shared.utils

import com.zip.shared.data.repository.companyProfile.HistoricalOption
import com.zip.shared.util.DateHelper
import com.zip.shared.util.extensions.formattedString
import org.junit.Test
import java.util.*


class DateHelperTest {

    @Test
    fun getDateByHistoricalOptionTest() {
        var historicalOption = HistoricalOption.Months_3
        var date = DateHelper.getDateByHistoricalOption(historicalOption)

        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.MONTH, -3)
        assert(date.formattedString().equals(calendar.time.formattedString()))

        historicalOption = HistoricalOption.Months_6
        date = DateHelper.getDateByHistoricalOption(historicalOption)
        calendar.time = currentDate
        calendar.add(Calendar.MONTH, -12)
        assert(date.formattedString().equals(calendar.time.formattedString()).not())
    }
}