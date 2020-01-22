package com.zip.challenge.companyProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zip.challenge.core.common.BaseViewModel
import com.zip.model.historicalPrice.HistoricalPrice
import com.zip.shared.api.Result
import com.zip.shared.data.repository.companyProfile.CompanyProfileRepository
import com.zip.shared.data.repository.companyProfile.HistoricalOption
import com.zip.shared.util.DateHelper
import com.zip.shared.util.extensions.formattedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * [CompanyProfileViewModel] acts as the intermediate channel between [CompanyProfileActivity] and
 * [CompanyProfileRepository]
 */
class CompanyProfileViewModel @Inject constructor(private val companyProfileRepository: CompanyProfileRepository) : BaseViewModel() {

   //Called by activity to listen for changes in the company details using LiveData
   fun companyBySympol(symbol: String) = companyProfileRepository.companyBySymbol(symbol)

   //Current historical option selection by the user
   var currentHistoricalOption = HistoricalOption.Unknown

   //New historical data observed by the activity
   private val _historicalData = MutableLiveData<List<HistoricalPrice>>()
   val historicalData: LiveData<List<HistoricalPrice>> = _historicalData

   //LiveData to listen for error in the network call
   private val _dataError = MutableLiveData<Boolean>()
   val dataError: LiveData<Boolean> = _dataError

   /**
    * Starts a coroutine to fetch historical data from the backend and update in room database.
    * @param symbol - company symbol
    * @param historicalOption - user selected option from options menu
    */
   fun historicalData(symbol: String?, historicalOption: HistoricalOption = HistoricalOption.Months_3) {
      if (currentHistoricalOption != historicalOption) {
         symbol?.let { companySymbol ->
            val toDate = toDate()
            val fromDate = fromDate(historicalOption)
            //Launch the coroutine
            viewModelScope.launch(Dispatchers.IO) {
               _isLoading.postValue(true)
               val result =
                  companyProfileRepository.historicalPrice(companySymbol, fromDate, toDate)
               _isLoading.postValue(false)
               when (result) {
                  is Result.Success ->{
                     currentHistoricalOption = historicalOption
                     _dataError.postValue(false)
                     _historicalData.postValue(result.data.historical)
                  }
                  is Result.Error -> _dataError.postValue(true)
               }
            }
         }
      }
   }

   fun toDate(): String = Date().formattedString()

   fun fromDate(historicalOption: HistoricalOption = currentHistoricalOption): String = DateHelper.getDateByHistoricalOption(historicalOption).formattedString()
}