package com.zip.shared.util.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zip.model.companyProfile.CompanyProfileResponse
import com.zip.model.stockList.StockListResponse
import com.zip.shared.data.database.stockList.StockListEntity

inline fun <reified VM: ViewModel> FragmentActivity.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM: ViewModel> Fragment.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

/**
 * Convert List of [StockListResponse] to List of [StockListEntity] to display in view
 */
fun StockListResponse.asStockListForView(): List<StockListEntity> {
    return this.symbolsList?.let {
        it.filter { item -> item.symbol.isNullOrEmpty().not() }
            .map { item -> StockListEntity(item.symbol ?: "", item.name, item.price) }
    } ?: emptyList()
}

/**
 * Convert List of [CompanyProfileResponse] to List of [StockListEntity] to display in view
 */
fun CompanyProfileResponse.asStockListForView(): List<StockListEntity> {
    return this.companyProfiles?.let {
        it.filter { item -> item.symbol.isNullOrEmpty().not() }
            .map { item -> StockListEntity( item.symbol ?: "", item.profile?.companyName, item.profile?.price, item.profile?.changesPercentage, item.profile?.image) }
    } ?: emptyList()
}