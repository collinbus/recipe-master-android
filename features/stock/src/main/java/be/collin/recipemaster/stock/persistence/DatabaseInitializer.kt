package be.collin.recipemaster.stock.persistence

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

class DatabaseInitializer : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        insertRefrigeratorStockItems(db)
    }

    private fun insertRefrigeratorStockItems(db: SupportSQLiteDatabase) {
        val tableName = "StockItem"
        val typeRefrigerator = 1
        val stockItemEntity1 = ContentValues().apply {
            val stockItem = StockItem(name = "Tomato", quantity = Quantity(2))
            put(StockItemEntity.KEY_STOCK_ITEM_ID, stockItem.id)
            put(StockItemEntity.KEY_STOCK_ITEM_NAME, stockItem.name)
            put(StockItemEntity.KEY_STOCK_ITEM_QUANTITY, stockItem.quantity.value)
            put(StockItemEntity.KEY_STOCK_ITEM_TYPE, typeRefrigerator)
        }
        val stockItemEntity2 = ContentValues().apply {
            val stockItem = StockItem(name = "Potato", quantity = Quantity(3))
            put(StockItemEntity.KEY_STOCK_ITEM_ID, stockItem.id)
            put(StockItemEntity.KEY_STOCK_ITEM_NAME, stockItem.name)
            put(StockItemEntity.KEY_STOCK_ITEM_QUANTITY, stockItem.quantity.value)
            put(StockItemEntity.KEY_STOCK_ITEM_TYPE, typeRefrigerator)
        }
        val stockItemEntity3 = ContentValues().apply {
            val stockItem = StockItem(name = "Milk 1L", quantity = Quantity(1))
            put(StockItemEntity.KEY_STOCK_ITEM_ID, stockItem.id)
            put(StockItemEntity.KEY_STOCK_ITEM_NAME, stockItem.name)
            put(StockItemEntity.KEY_STOCK_ITEM_QUANTITY, stockItem.quantity.value)
            put(StockItemEntity.KEY_STOCK_ITEM_TYPE, typeRefrigerator)
        }
        db.insert(tableName, SQLiteDatabase.CONFLICT_REPLACE, stockItemEntity1)
        db.insert(tableName, SQLiteDatabase.CONFLICT_REPLACE, stockItemEntity2)
        db.insert(tableName, SQLiteDatabase.CONFLICT_REPLACE, stockItemEntity3)
    }
}