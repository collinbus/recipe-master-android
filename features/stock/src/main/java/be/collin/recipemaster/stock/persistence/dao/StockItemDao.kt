package be.collin.recipemaster.stock.persistence.dao

import androidx.room.*
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Dao
interface StockItemDao {
    @Query("SELECT * FROM StockItem")
    suspend fun getStockItems(): List<StockItemEntity>
    @Insert(entity = StockItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStockItems(vararg stockItemEntity: StockItemEntity)
    @Delete
    suspend fun removeStockItem(stockItem: StockItemEntity)
}