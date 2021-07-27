package be.collin.recipemaster.stock.persistence.repository

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockItemRepository(private val refrigeratorDao: RefrigeratorDao) {
    suspend fun getRefrigeratorStockItems(): StockItems = StockItems(
        withContext(Dispatchers.IO) {
            refrigeratorDao.getRefrigeratorStockItems().map {
                StockItem(it.id, it.name, Quantity(it.quantity))
            }
        }
    )

    fun insertRefrigeratorStockItems(stockItems: StockItems) {
        stockItems.forEach {
            refrigeratorDao.addRefrigeratorStockItem(
                StockItemEntity(
                    it.id,
                    it.name,
                    it.quantity.value,
                    REFRIGERATOR_STOCK_ITEM_TYPE
                )
            )
        }
    }

    companion object {
        private const val REFRIGERATOR_STOCK_ITEM_TYPE = 1
    }
}