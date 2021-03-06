package be.collin.recipemaster.stock.stockitem

import be.collin.recipemaster.stock.StockItem

interface StockItemChangedListener {
    fun nameChanged(newName: String, stockItem: StockItem)
    fun quantityIncreased(stockItem: StockItem)
    fun quantityDecreased(stockItem: StockItem)
}