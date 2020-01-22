package com.zip.challenge.companyProfile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.zip.challenge.R
import com.zip.challenge.core.common.BaseActivity
import com.zip.challenge.core.ui.CompanyUIHelper
import com.zip.challenge.databinding.ActivityCompanyProfileBinding
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.data.repository.companyProfile.HistoricalOption
import com.zip.shared.frameworks.chart.ChartHelper
import com.zip.shared.frameworks.image.ImageLoader
import com.zip.shared.services.RefreshRealtimePriceService
import com.zip.shared.util.Constants
import com.zip.shared.util.extensions.doOnChange
import com.zip.shared.util.extensions.dollarString
import com.zip.shared.util.extensions.emptyIfNull
import com.zip.shared.util.extensions.viewModelProvider
import kotlinx.android.synthetic.main.activity_company_profile.*

/**
 * [CompanyProfileActivity] activity display the details of the company such as
 * name, price, change percentage, industry, sector. This activity also shows a line chart
 * of the price changes for the company over the last 3 months, 6 months, 1 year and 3 years.
 * Also, the price of the company gets refreshed every 15 seconds.
 */
class CompanyProfileActivity : BaseActivity() {

    private lateinit var viewModel: CompanyProfileViewModel
    private lateinit var binding: ActivityCompanyProfileBinding

    //Symbol of the company passed as an intent extra. For eg: AMZN, AAPL
    private var symbol: String? = null

    //Handler to start a runnable every 15 seconds
    private val handler: Handler = Handler()

    /**
    This runnable starts [RefreshRealtimePriceService] to fetch the real time price
     */
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            startService(
                Intent(this@CompanyProfileActivity, RefreshRealtimePriceService::class.java)
                    .apply { putExtra(Constants.EXTRA_SYMBOL, symbol) }
            )
            handler.postDelayed(this, Constants.REFRESH_REAL_TIME_PRICE_INTERVAL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_profile)
        viewModel = viewModelProvider(viewModelFactory)
        binding.apply {
            lifecycleOwner = this@CompanyProfileActivity
            viewModel = this@CompanyProfileActivity.viewModel
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = viewModelProvider(viewModelFactory)
        if (intent?.hasExtra(Constants.EXTRA_SYMBOL) == true) {
            symbol = intent?.getStringExtra(Constants.EXTRA_SYMBOL)
        }

        supportActionBar?.title = symbol ?: ""
        observeViewModel()

        //Get the historical data for this symbol
        viewModel.historicalData(symbol)
    }

    private fun observeViewModel() {
        symbol?.let {
            viewModel.companyBySympol(it).doOnChange(this) { company ->
                //Update the view wth the new data
                populateViews(company)
            }

            viewModel.historicalData.doOnChange(this) { historicalPriceList ->
                //Show the line chart for the selected option (last 3 months, last 6 months, last 1 year, last 3 years)
                lineChartTitle.text = getString(R.string.line_chart_title)
                    .format(viewModel.fromDate(), viewModel.toDate())
                ChartHelper.displayHistoricalLineChart(lineChart, it, historicalPriceList, viewModel.currentHistoricalOption)
            }

            viewModel.dataError.doOnChange(this) { error ->
                if (error) showToast(getString(R.string.historical_data_error))
            }
        }
    }

    private fun populateViews(company: StockListEntity) {
        stockItemSymbolTextView.text = company.symbol
        stockItemNameTextView.text = company.name.emptyIfNull()
        stockItemPriceTextView.text = company.price.dollarString()
        CompanyUIHelper.showChangePercent(stockItemChangeTextView, company.changePercent)
        ImageLoader.loadImage(stockItemImageView, company.image ?: "")
    }

    //Inflate options menu to select historical option type
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.historical_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.show_3_months -> viewModel.historicalData(symbol, HistoricalOption.Months_3)
            R.id.show_6_months -> viewModel.historicalData(symbol, HistoricalOption.Months_6)
            R.id.show_1_year -> viewModel.historicalData(symbol, HistoricalOption.Year_1)
            R.id.show_3_years -> viewModel.historicalData(symbol, HistoricalOption.Year_3)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        //start handler to execute runnable
        handler.postDelayed(runnable, 0)
    }

    override fun onPause() {
        super.onPause()
        //stop handler
        handler.removeCallbacks(runnable)
    }
}