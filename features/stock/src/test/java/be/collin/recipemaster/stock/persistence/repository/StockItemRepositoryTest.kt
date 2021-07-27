package be.collin.recipemaster.stock.persistence.repository

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

internal class StockItemRepositoryTest : StringSpec({
    "should return StockItems when getRefrigeratorStockItems is called" {
        val id1 = "firstId"
        val name1 = "name1"
        val quantity1 = 5
        val stockItemType = 1
        val id2 = "secondId"
        val name2 = "name2"
        val quantity2 = 10
        val refrigeratorDao: RefrigeratorDao = mockk {
            coEvery { getRefrigeratorStockItems() } returns listOf(
                StockItemEntity(id1, name1, quantity1, stockItemType),
                StockItemEntity(id2, name2, quantity2, stockItemType),
            )
        }
        val repository = StockItemRepository(refrigeratorDao)

        val stockItems = repository.getRefrigeratorStockItems()

        stockItems shouldBe StockItems(
            mutableListOf(
                StockItem(id1, name1, Quantity(quantity1)),
                StockItem(id2, name2, Quantity(quantity2))
            )
        )
    }

    "should call insert on dao with correct stock item type when an item is added" {
        val refrigeratorDao: RefrigeratorDao = mockk()
        val repository = StockItemRepository(refrigeratorDao)
        val id = "id"
        val name = "name"
        val quantity = Quantity(5)

        repository.insertRefrigeratorStockItems(StockItems(mutableListOf(StockItem(id, name, quantity))))

        coVerify { refrigeratorDao.addRefrigeratorStockItem(StockItemEntity(id, name, quantity.value, 1)) }
    }

    "should call remove on dao with correct stock item type when an item is added" {
        val refrigeratorDao: RefrigeratorDao = mockk()
        val repository = StockItemRepository(refrigeratorDao)
        val id = "id"
        val name = "name"
        val quantity = Quantity(5)

        repository.removeRefrigeratorStockItem(StockItem(id, name, quantity))

        coVerify { refrigeratorDao.removeStockItem(StockItemEntity(id, name, quantity.value, 1)) }
    }
})