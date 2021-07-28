package be.collin.recipemaster.stock.stockitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems

abstract class StockItemViewModel : ViewModel() {
    abstract fun increaseQuantityOf(stockItem: StockItem)
    abstract fun decreaseQuantityOf(stockItem: StockItem)
    abstract fun changeName(newName: String, stockItem: StockItem)
    abstract fun saveStockItems()
    abstract fun addStockItem()
    abstract fun removeItemAt(position: Int)

    abstract val uiState: LiveData<UIState>

    sealed class UIState {
        data class Initialized(
            val stockItems: StockItems
        ) : UIState()

        data class Updated(
            val stockItem: StockItem
        ) : UIState()

        data class Added(
            val stockItem: StockItem
        ) : UIState()

        data class Removed(
            val position: Int
        ) : UIState()
    }
}