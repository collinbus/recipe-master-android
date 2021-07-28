package be.collin.recipemaster.stock.persistence.dao

import androidx.room.*
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Dao
abstract class StockItemDao {
    @Query("SELECT * FROM StockItem")
    abstract suspend fun getStockItems(): List<StockItemEntity>
    @Insert(entity = StockItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addStockItems(vararg stockItemEntity: StockItemEntity)
    @Delete
    abstract suspend fun removeStockItem(stockItem: StockItemEntity)
}