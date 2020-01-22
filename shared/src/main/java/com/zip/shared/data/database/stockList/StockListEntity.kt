package com.zip.shared.data.database.stockList

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [StockListEntity] is the entity for the database.
 */
@Entity(tableName = "stocks_list")
data class StockListEntity(
    @PrimaryKey val symbol: String,
    val name: String?,
    val price: Double?,
    val changePercent: String? = null,
    val image: String? = null,
    val isFavourite: Boolean = false)