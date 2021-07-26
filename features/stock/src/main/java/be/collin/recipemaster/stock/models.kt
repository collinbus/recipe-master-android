package be.collin.recipemaster.stock

data class StockItems(private val stockItems: List<StockItem>) {
    val size: Int get() = stockItems.size
    operator fun get(position: Int): StockItem = stockItems[position]
}

data class StockItem(val name: String, val quantity: Quantity)

data class Quantity(private var quantity: Int) {
    val value: Int get() = quantity
}
