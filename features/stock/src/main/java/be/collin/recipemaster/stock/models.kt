package be.collin.recipemaster.stock

import java.util.*

data class StockItems(private val stockItems: List<StockItem>) {
    val size: Int get() = stockItems.size

    operator fun get(position: Int): StockItem = stockItems[position]

    fun update(stockItem: StockItem) {
        val targetStockItem = stockItems.firstOrNull { it.id == stockItem.id }
        targetStockItem?.update(stockItem)
    }

    fun indexOf(stockItem: StockItem): Int = stockItems.indexOf(stockItem)

    fun forEach(action: (StockItem) -> Unit) = stockItems.forEach(action)
}

data class StockItem(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var quantity: Quantity
) {
    fun update(stockItem: StockItem) {
        this.name = stockItem.name
        this.quantity = stockItem.quantity
    }
}

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
