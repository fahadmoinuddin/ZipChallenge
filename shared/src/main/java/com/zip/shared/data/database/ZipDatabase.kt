package com.zip.shared.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zip.shared.BuildConfig
import com.zip.shared.data.database.stockList.StockListDao
import com.zip.shared.data.database.stockList.StockListEntity

/**
 * [ZipDatabase] is a room database to store all stocks, their profile and favourites of the user.
 */
@Database(entities = [StockListEntity::class], version = 1, exportSchema = false)
abstract class ZipDatabase : RoomDatabase() {

    //Dao instance
    abstract fun stockListDao(): StockListDao

    companion object {
        private const val databaseName = BuildConfig.ROOM_DB_NAME

        //Get db instance
        fun buildDatabase(context: Context): ZipDatabase {
            return Room.databaseBuilder(context, ZipDatabase::class.java, databaseName)
                .build()
        }
    }
}