package be.collin.recipemaster.stock.persistence.repository

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.dao.StockItemDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockItemRepository<T:StockItemDao>(private val stockItemDao: T) {
    suspend fun getRefrigeratorStockItems(): StockItems = StockItems(
        withContext(Dispatchers.IO) {
            stockItemDao.getStockItems().map {
                StockItem(it.id, it.name, Quantity(it.quantity))
            }.toMutableList()
        }
    )

    suspend fun insertRefrigeratorStockItems(stockItems: StockItems) {
        withContext(Dispatchers.IO) {
            val refrigeratorItems = mutableListOf<StockItemEntity>()
            stockItems.forEach {
                refrigeratorItems.add(
                    StockItemEntity(
                        it.id,
                        it.name,
                        it.quantity.value,
                        REFRIGERATOR_STOCK_ITEM_TYPE
                    )
                )
            }
            stockItemDao.addStockItems(*refrigeratorItems.toTypedArray())
        }
    }

    suspend fun removeRefrigeratorStockItem(stockItem: StockItem) {
        withContext(Dispatchers.IO) {
            stockItemDao.removeStockItem(
                StockItemEntity(
                    stockItem.id, stockItem.name, stockItem.quantity.value,
                    REFRIGERATOR_STOCK_ITEM_TYPE
                )
            )
        }
    }

    companion object {
        private const val REFRIGERATOR_STOCK_ITEM_TYPE = 1
    }
}