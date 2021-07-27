package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.repository.StockItemRepository

class RefrigeratorViewModelImpl(
    private val repository: StockItemRepository
) : RefrigeratorViewModel() {

    private lateinit var stockItems: StockItems

    private val updateLiveData = MutableLiveData<UIState>()

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(liveData {
            stockItems = repository.getStockItems()
            emit(UIState.Initialized(stockItems))
        }) { value = it }
        addSource(updateLiveData) { value = it }
    }

    override fun increaseQuantityOf(stockItem: StockItem) {
        stockItem.quantity.increment()
        updateLiveData.value = UIState.Updated(stockItem)
    }

    override fun decreaseQuantityOf(stockItem: StockItem) {
        stockItem.quantity.decrement()
        updateLiveData.value = UIState.Updated(stockItem)
    }

    override fun changeName(newName: String, stockItem: StockItem) {
        if (stockItem.name != newName) {
            stockItem.name = newName
        }
    }
}