package be.collin.recipemaster.stock

data class StockItems(private val stockItems: List<StockItem>) {
    val size: Int get() = stockItems.size

    operator fun get(position: Int): StockItem = stockItems[position]

    fun update(stockItem: StockItem) {
        val targetStockItem = stockItems.firstOrNull { it.name == stockItem.name }
        targetStockItem?.quantity = stockItem.quantity
    }

    fun indexOf(stockItem: StockItem): Int = stockItems.indexOf(stockItem)
}

data class StockItem(var name: String, var quantity: Quantity)

data class Quantity(private var quantity: Int) {
    val value: Int get() = quantity

    fun increment() {
        quantity++
    }

    fun decrement() {
        if (quantity > 0)
            quantity--
    }
}
