package com.zip.shared.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zip.shared.data.database.ZipDatabase
import com.zip.shared.util.LiveDataTestUtil
import com.zip.shared.util.extensions.asStockListForView
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for stock list database CRUD
 */
@RunWith(AndroidJUnit4::class)
class StockListDaoTest {

    //Executes live data in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var zipDatabase: ZipDatabase

    @Before
    fun initDb() {
        zipDatabase = ZipDatabase.buildDatabase(ApplicationProvider.getApplicationContext())
    }

    @After
    fun closeDb() {
        zipDatabase.close()
    }

    @Test
    fun insertStocks() {
        val stockListDao = zipDatabase.stockListDao()
        val entityList = DataFactory.getStockList().asStockListForView()
        runBlocking {
            stockListDao.insert(entityList)
        }

        val insertedEntityList = LiveDataTestUtil.getValue(stockListDao.stockList())
        assert(insertedEntityList?.size == 5)
    }

    @Test
    fun updateStock() {
        val stockListDao = zipDatabase.stockListDao()
        runBlocking {
            stockListDao.updateStockListEntity(DataFactory.getSPYCompanyStock().asStockListForView()[0])
        }

        val updateEntity = LiveDataTestUtil.getValue(stockListDao.companyLiveDataFromSymbol("SPY"))
        assert(updateEntity?.price == 332.92)
    }

    @Test
    fun deleteStocks() {
        val stockListDao = zipDatabase.stockListDao()
        runBlocking {
            stockListDao.deleteAll()
        }

        val updateEntity = LiveDataTestUtil.getValue(stockListDao.stockList())
        assert(updateEntity.isNullOrEmpty())
    }
}