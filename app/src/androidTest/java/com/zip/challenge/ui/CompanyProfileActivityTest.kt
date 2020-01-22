package com.zip.challenge.ui

import android.content.Intent
import android.os.SystemClock
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.zip.challenge.R
import com.zip.challenge.companyProfile.CompanyProfileActivity
import com.zip.challenge.utils.DataFactory
import com.zip.shared.data.database.ZipDatabase
import com.zip.shared.util.Constants
import com.zip.shared.util.extensions.asStockListForView
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompanyProfileActivityTest {

    @get:Rule
    var activityRule =
        object : ActivityTestRule<CompanyProfileActivity>(CompanyProfileActivity::class.java) {
            override fun getActivityIntent(): Intent {
                return Intent(Intent.ACTION_MAIN).apply {
                    putExtra(Constants.EXTRA_SYMBOL, "SPY")
                }
            }
        }

    private lateinit var zipDatabase: ZipDatabase

    @Before
    fun initDb() {
        zipDatabase = ZipDatabase.buildDatabase(ApplicationProvider.getApplicationContext())
        insertStocks()
    }

    @After
    fun closeDb() {
        zipDatabase.close()
    }

    fun insertStocks() {
        val stockListDao = zipDatabase.stockListDao()
        val entityList = DataFactory.getStockList().asStockListForView()
        runBlocking {
            stockListDao.insert(entityList)
        }
    }

    @Test
    fun details_basicViewsDisplayed() {
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbar))
            )
        ).check(matches(withText("SPY")))

        SystemClock.sleep(1000)

        onView(withId(R.id.stockItemPriceTextView))
            .check(matches(withText("US$ 331.95")))
    }
}