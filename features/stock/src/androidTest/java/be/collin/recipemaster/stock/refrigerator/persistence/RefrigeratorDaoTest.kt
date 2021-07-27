package be.collin.recipemaster.stock.refrigerator.persistence

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.stock.persistence.AppDatabase
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RefrigeratorDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: RefrigeratorDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .addCallback(MockDatabaseInitializer())
            .build()
        dao = db.refrigeratorDao()
    }

    private class MockDatabaseInitializer : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            db.insert("StockItem", SQLiteDatabase.CONFLICT_REPLACE, ContentValues().apply {
                put(StockItemEntity.KEY_STOCK_ITEM_ID, "id1")
                put(StockItemEntity.KEY_STOCK_ITEM_NAME, "name1")
                put(StockItemEntity.KEY_STOCK_ITEM_QUANTITY, 5)
                put(StockItemEntity.KEY_STOCK_ITEM_TYPE, 1)
            })
            db.insert("StockItem", SQLiteDatabase.CONFLICT_REPLACE, ContentValues().apply {
                put(StockItemEntity.KEY_STOCK_ITEM_ID, "id2")
                put(StockItemEntity.KEY_STOCK_ITEM_NAME, "name2")
                put(StockItemEntity.KEY_STOCK_ITEM_QUANTITY, 10)
                put(StockItemEntity.KEY_STOCK_ITEM_TYPE, 1)
            })
        }
    }

    @Test
    fun shouldGetCorrectStockItems() {
        val refrigeratorStockItems = dao.getRefrigeratorStockItems()

        refrigeratorStockItems shouldContain StockItemEntity("id1","name1", 5, 1)
        refrigeratorStockItems shouldContain StockItemEntity("id2","name2", 10, 1)
    }

    @Test
    fun shouldUpdateStockItemsCorrectly() {
        dao.addRefrigeratorStockItem(StockItemEntity("id1","another name", 6, 1))

        val refrigeratorStockItems = dao.getRefrigeratorStockItems()

        refrigeratorStockItems shouldContain StockItemEntity("id1","another name", 6, 1)
        refrigeratorStockItems.size shouldBe 2
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }
}