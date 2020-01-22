package com.zip.shared.data.database.stockList

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * [StockListDao] contains all the queries for the tables
 */
@Dao
interface StockListDao {

    //Returns all stocks
    @Query("Select * from stocks_list")
    fun stockList(): LiveData<List<StockListEntity>>

    //Returns stocks based on symbol
    @Query("Select * from stocks_list where symbol = :symbol")
    suspend fun companyFromSymbol(symbol: String): StockListEntity?

    //Returns stocks livedata based on symbol
    @Query("Select * from stocks_list where symbol = :symbol")
    fun companyLiveDataFromSymbol(symbol: String): LiveData<StockListEntity>

    //Retruns all favourite stocks
    @Query("Select * from stocks_list where isFavourite = 1")
    fun favouriteStocks(): LiveData<List<StockListEntity>>

    //Returns all favourite symbols
    @Query("Select symbol from stocks_list where isFavourite = 1")
    suspend fun favouriteSymbols(): List<String>

    //Inserts data. If row already exists, replace the row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockList: List<StockListEntity>)

    //Update the row
    @Update
    suspend fun updateStockListEntity(company: StockListEntity): Int

    //Delete all rows
    @Query("Delete from stocks_list")
    suspend fun deleteAll()

}