package com.zip.shared.frameworks.chart

import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.zip.model.historicalPrice.HistoricalPrice
import com.zip.shared.R
import com.zip.shared.data.repository.companyProfile.HistoricalOption
import com.zip.shared.util.extensions.formattedString
import java.util.*

data class ChartData(val xAxisValues: List<String>, val entries: List<Entry>)

/**
 * [ChartHelper] is a helper class to handle chart operations and display it to the user.
 */
object ChartHelper {

    /**
     * Draw line chart for historical data
     * @param view - line chart view
     * @param symbol - symbol to show the historical data for
     * @param historyList - historical data
     * @param historicalOption - selected option (Last 3 months, Last 6 months, Last 1 year, Last 3 years)
     */
    fun displayHistoricalLineChart(view: View, symbol: String, historyList: List<HistoricalPrice>, historicalOption: HistoricalOption) {
        (view as? LineChart)?.let { lineChart ->
            // Chart properties
            lineChart.description.isEnabled = false
            lineChart.setDrawGridBackground(false)

            // Group chart data from historical data
            val chartData = getData(historyList, historicalOption)

            //Set the data set for the line
            val lineDataSet = LineDataSet(chartData.entries, "Closing price for $symbol")
            lineDataSet.color = ContextCompat.getColor(lineChart.context, R.color.line_chart_color)
            lineDataSet.valueTextColor =
                ContextCompat.getColor(lineChart.context, R.color.line_chart_text_color)

            //x-axis properties
            val xAxis = lineChart.xAxis
            xAxis.granularity = 1f
            xAxis.textColor = ContextCompat.getColor(lineChart.context, R.color.line_chart_text_color)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            val formatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return if (value.toInt() < chartData.xAxisValues.size) chartData.xAxisValues[value.toInt()] else ""
                }
            }
            xAxis.valueFormatter = formatter
            xAxis.labelRotationAngle = 90f

            // y-axis properties
            val yAxisRight = lineChart.axisRight
            yAxisRight.isEnabled = false

            val yAxisLeft = lineChart.axisLeft
            yAxisLeft.granularity = 1f
            yAxisLeft.textColor = ContextCompat.getColor(lineChart.context, R.color.line_chart_text_color)

            //Set the data for the line chart
            val data = LineData(lineDataSet)
            lineChart.data = data
            lineChart.animateX(2500)

            //Update the legend for the chart
            val legend = lineChart.legend
            legend.textColor = ContextCompat.getColor(lineChart.context, R.color.line_chart_text_color)
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

            //Draw the chart
            lineChart.invalidate()
        }
    }

    private fun getData(historyList: List<HistoricalPrice>, historicalOption: HistoricalOption): ChartData {
        val xAxisValues = arrayListOf<String>() // x-axis labels
        val entries = arrayListOf<Entry>() // entries for plotting

        //Get the grouped data
        getGroupedData(historyList, historicalOption).entries.forEachIndexed { index, entry ->
            var label = ""
            var averageClosePrice = 0f
            if (entry.value.isNotEmpty()) {
                val groupedList = entry.value
                label = groupedList[0].date.formattedString(historicalOption.dateFormatForChart()) // label for x-axis
                val closePriceList: List<Double> = groupedList.mapNotNull { it.close }
                averageClosePrice = closePriceList.average().toFloat() // calculate the average price for the group to plot
            }
            xAxisValues.add(label)
            entries.add(Entry(index.toFloat(), averageClosePrice))
        }

        return ChartData(xAxisValues, entries)
    }

    /**
     * Helper function to segregate historical data into groups to display in chart.
     * If option is last 3 months or last 6 months - data is grouped weekly
     * If option is last 1 year or last 3 years - data is grouped monthly
     */
    private fun getGroupedData(historyList: List<HistoricalPrice>, historicalOption: HistoricalOption): Map<String, List<HistoricalPrice>> {
        val calendar = Calendar.getInstance()
        val currentDate = Date()
        return historyList.groupBy {
            calendar.time = it.date ?: currentDate
            "${calendar.get(historicalOption.calendarOptionForChart())}${calendar.get(Calendar.YEAR)}"
        }
    }
}