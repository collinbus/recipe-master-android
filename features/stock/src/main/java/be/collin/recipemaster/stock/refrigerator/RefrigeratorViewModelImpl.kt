package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems

class RefrigeratorViewModelImpl : RefrigeratorViewModel() {

    private val initialState = liveData {
        val firstName = "Tomato"
        val firstQuantity = 2
        val secondName = "Potato"
        val secondQuantity = 3
        val thirdName = "Milk 1L"
        val thirdQuantity = 1
        emit(
            UIState.Initialized(
                StockItems(
                    listOf(
                        StockItem(firstName, Quantity(firstQuantity)),
                        StockItem(secondName, Quantity(secondQuantity)),
                        StockItem(thirdName, Quantity(thirdQuantity)),
                    )
                )
            ) as UIState
        )
    }

    private val updateLiveData = MutableLiveData<UIState>()

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(initialState) { value = it }
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
}