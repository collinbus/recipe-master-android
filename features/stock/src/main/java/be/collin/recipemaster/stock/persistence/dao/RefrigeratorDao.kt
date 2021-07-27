package be.collin.recipemaster.stock.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Dao
interface RefrigeratorDao {
    @Query("SELECT * FROM StockItem WHERE stockItemType = 1")
    fun getRefrigeratorStockItems(): List<StockItemEntity>
}