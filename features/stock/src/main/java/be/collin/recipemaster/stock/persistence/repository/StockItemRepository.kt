package be.collin.recipemaster.stock.persistence.repository

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.dao.ShoppingListDao
import be.collin.recipemaster.stock.persistence.dao.StockItemDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

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
                        getStockItemType()
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
                    getStockItemType()
                )
            )
        }
    }

    private fun getStockItemType(): Int {
        if (stockItemDao is RefrigeratorDao)
            return REFRIGERATOR_STOCK_ITEM_TYPE
        return SHOPPING_LIST_STOCK_ITEM_TYPE
    }

    companion object {
        private const val REFRIGERATOR_STOCK_ITEM_TYPE = 1
        private const val SHOPPING_LIST_STOCK_ITEM_TYPE = 2
    }
}