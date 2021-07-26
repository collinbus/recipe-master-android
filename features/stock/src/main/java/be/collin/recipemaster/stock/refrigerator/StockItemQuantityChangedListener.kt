package be.collin.recipemaster.stock.refrigerator

import be.collin.recipemaster.stock.StockItem

interface StockItemQuantityChangedListener {
    fun quantityIncreased(stockItem: StockItem)
    fun quantityDecreased(stockItem: StockItem)
}