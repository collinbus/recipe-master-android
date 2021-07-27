package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.persistence.repository.StockItemRepository
import kotlinx.coroutines.launch

class RefrigeratorViewModelImpl(
    private val repository: StockItemRepository
) : RefrigeratorViewModel() {

    private var stockItems: StockItems? = null

    private val updateLiveData = MutableLiveData<UIState>()

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(liveData {
            stockItems = repository.getRefrigeratorStockItems()
            emit(UIState.Initialized(stockItems!!))
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

    override fun saveStockItems() {
        viewModelScope.launch {
            stockItems?.let {
                repository.insertRefrigeratorStockItems(it)
            }
        }
    }

    override fun addStockItem() {
        val stockItem = StockItem(name = "New item", quantity = Quantity(1))
        viewModelScope.launch {
            repository.insertRefrigeratorStockItems(StockItems(mutableListOf(stockItem)))
        }
        updateLiveData.value = UIState.Added(stockItem)
    }
}