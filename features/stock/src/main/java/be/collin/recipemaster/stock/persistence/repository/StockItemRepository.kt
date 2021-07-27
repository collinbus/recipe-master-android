package be.collin.recipemaster.stock.persistence.repository

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockItemRepository(private val refrigeratorDao: RefrigeratorDao) {
    suspend fun getStockItems(): StockItems = StockItems(
        withContext(Dispatchers.IO) {
            refrigeratorDao.getRefrigeratorStockItems().map {
                StockItem(it.id, it.name, Quantity(it.quantity))
            }
        }
    )
}