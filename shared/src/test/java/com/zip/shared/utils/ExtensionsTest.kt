package com.zip.shared.utils

import com.zip.shared.util.extensions.DateFormat
import com.zip.shared.util.extensions.dollarString
import com.zip.shared.util.extensions.formattedString
import org.junit.Test
import java.util.*

class ExtensionsTest {

    @Test
    fun formattedDateTest_NullDate() {
        val date = null
        assert(date.formattedString(DateFormat.MMM_yy).equals(""))
    }

    @Test
    fun dollarStringTest() {
        var price: Double? = 320.135
        assert(price.dollarString().equals("US$ 320.13"))

        price = 1320.135
        assert(price.dollarString().equals("US$ 1,320.13"))

        price = null
        assert(price.dollarString().equals(""))
    }
}