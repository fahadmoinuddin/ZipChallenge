package com.zip.challenge.home.favoruites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zip.challenge.companyProfile.CompanyProfileActivity
import com.zip.challenge.companyProfile.CompanyProfileViewModel
import com.zip.challenge.core.common.BaseViewModel
import com.zip.shared.api.Result
import com.zip.shared.data.database.stockList.StockListEntity
import com.zip.shared.data.repository.companyProfile.CompanyProfileRepository
import com.zip.shared.data.repository.favourites.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [UpdateFavourites] to be implemented by views which have favourite functionality
 */
interface UpdateFavourites {
    fun updateFavouriteStatus(symbol: String)
}

/**
 * [FavouritesViewModel] acts as the intermediate channel between [FavouritesFragment] and
 * [FavouritesRepository]
 */
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository) :
    BaseViewModel(), UpdateFavourites {

    //
    val favouritesListData: LiveData<List<StockListEntity>> = repository.favouriteStocks

    private val _favouritesEmpty = MutableLiveData<Boolean>()
    val favouritesEmpty: LiveData<Boolean> = _favouritesEmpty

    private val _favouriteStock = MutableLiveData<StockListEntity>()
    val favouriteStock: LiveData<StockListEntity> = _favouriteStock

    override fun updateFavouriteStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFavouriteStatus(symbol)
            when (result) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }

    fun isFavouritesEmpty(empty: Boolean) {
        _favouritesEmpty.postValue(empty)
    }
}