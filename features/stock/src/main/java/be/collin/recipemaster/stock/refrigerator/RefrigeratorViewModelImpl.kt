package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(initialState) { value = it }
    }

    override fun increaseQuantityOf(stockItem: StockItem) {
        TODO("Not yet implemented")
    }

    override fun decreaseQuantityOf(stockItem: StockItem) {
        TODO("Not yet implemented")
    }
}