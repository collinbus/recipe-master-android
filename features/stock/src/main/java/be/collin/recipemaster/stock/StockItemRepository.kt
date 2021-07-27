package be.collin.recipemaster.stock

class StockItemRepository {
    fun getStockItems(): StockItems = StockItems(
        listOf(
            StockItem(name = "Tomato", quantity = Quantity(2)),
            StockItem(name = "Potato", quantity = Quantity(3)),
            StockItem(name = "Milk 1L", quantity = Quantity(1)),
        )
    )
}