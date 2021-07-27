package be.collin.recipemaster.stock.persistence.dao

import androidx.room.*
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Dao
interface RefrigeratorDao {
    @Query("SELECT * FROM StockItem WHERE stockItemType = 1")
    suspend fun getRefrigeratorStockItems(): List<StockItemEntity>

    @Insert(entity = StockItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRefrigeratorStockItem(vararg stockItemEntity: StockItemEntity)
    @Delete
    suspend fun removeStockItem(stockItem: StockItemEntity)
}