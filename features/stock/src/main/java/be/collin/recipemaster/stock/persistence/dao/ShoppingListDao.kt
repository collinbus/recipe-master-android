package be.collin.recipemaster.stock.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Dao
abstract class ShoppingListDao : StockItemDao() {
    @Query("SELECT * FROM StockItem WHERE stockItemType = 2")
    abstract override suspend fun getStockItems(): List<StockItemEntity>
}