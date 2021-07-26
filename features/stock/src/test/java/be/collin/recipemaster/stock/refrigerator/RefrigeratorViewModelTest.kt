package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.Observer
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.spyk
import io.mockk.verify

internal class RefrigeratorViewModelTest: BehaviorSpec({
    given("a RefrigeratorViewModel") {
        val observer: Observer<RefrigeratorViewModel.UIState> = spyk()

        val viewModel = RefrigeratorViewModelImpl()
        `when`("the viewModel is created") {
            viewModel.uiState.observeForever(observer)

            then("it emit the correct ui state") {
                val firstName = "Tomato"
                val firstQuantity = 2
                val secondName = "Potato"
                val secondQuantity = 3
                val thirdName = "Milk 1L"
                val thirdQuantity = 1
                val expectedUIState = RefrigeratorViewModel.UIState.Initialized(
                    StockItems(
                        listOf(
                            StockItem(firstName, Quantity(firstQuantity)),
                            StockItem(secondName, Quantity(secondQuantity)),
                            StockItem(thirdName, Quantity(thirdQuantity)),
                        )
                    )
                )
                verify { observer.onChanged(expectedUIState) }
            }
        }
    }
})