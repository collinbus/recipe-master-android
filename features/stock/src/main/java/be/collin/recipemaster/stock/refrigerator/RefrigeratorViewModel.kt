package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems

abstract class RefrigeratorViewModel : ViewModel() {
    abstract fun increaseQuantityOf(stockItem: StockItem)
    abstract fun decreaseQuantityOf(stockItem: StockItem)
    abstract fun changeName(newName: String, stockItem: StockItem)
    abstract fun saveStockItems()
    abstract fun addStockItem()

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
    }
}