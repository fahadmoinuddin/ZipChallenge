package com.zip.challenge.home.stockList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zip.challenge.core.common.BaseViewModel
import com.zip.challenge.home.favoruites.UpdateFavourites
import com.zip.shared.api.Result
import com.zip.shared.api.succeeded
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.data.repository.BaseStocksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [StockListViewModel] acts as the intermediate channel between [StockListFragment] and
 * [BaseStocksRepository]
 */
class StockListViewModel @Inject constructor(private val repository: BaseStocksRepository) :
    BaseViewModel(), UpdateFavourites {

    //LiveData for all the stocks
    val stockListData = repository.allStocks

    private val _stockListSuccess = MutableLiveData<Boolean>()
    val stockListSuccess: LiveData<Boolean> = _stockListSuccess

    //LiveData to show retry option in case of error
    private val _stockListError = MutableLiveData<Boolean>()
    val stockListError: LiveData<Boolean> = _stockListError

    //LiveData to show add/remove status as toast message
    private val _favouriteStock = MutableLiveData<StockListEntity?>()
    val favouriteStock: LiveData<StockListEntity?> = _favouriteStock

    //Load all stocks from the backend
    fun loadStockList() {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                val result = repository.stockList()
                _isLoading.postValue(false)
                when (result) {
                    is Result.Success -> {
                        if (result.succeeded) {
                            _stockListSuccess.postValue(result.data)
                            _stockListError.postValue(false)
                        } else {
                            _stockListError.postValue(true)
                        }
                    }
                    is Result.Error -> _stockListError.postValue(true)
                }
            }
        }
    }

    //Fetch company profiles for symbols when the user scrolls through the list
    fun fetchCompanyProfiles(symbolsList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchCompanyProfiles(symbolsList)
        }
    }

    //Update favourite status in room database
    override fun updateFavouriteStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFavouriteStatus(symbol)
            when (result) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }

        }
    }

    fun clearFavouriteStock() {
        _favouriteStock.postValue(null)
    }
}