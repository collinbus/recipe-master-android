package be.collin.recipemaster.stock.stockitem

import be.collin.recipemaster.stock.StockItem

data class StockItemUIModel(private val stockItem: StockItem) {
    val name = stockItem.name
    val quantity = "${stockItem.quantity.value}"
}