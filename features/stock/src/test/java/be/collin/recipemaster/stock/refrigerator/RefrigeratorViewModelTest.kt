package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.Observer
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifySequence

internal class RefrigeratorViewModelTest: BehaviorSpec({
    given("a RefrigeratorViewModel") {
        val observer: Observer<RefrigeratorViewModel.UIState> = spyk()

        val viewModel = RefrigeratorViewModelImpl()
        viewModel.uiState.observeForever(observer)

        `when`("the viewModel is created") {

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

        `when`("the quantity of an item is increased") {
            val updatedStockItem = StockItem("Tomato", Quantity(2))
            viewModel.increaseQuantityOf(updatedStockItem)
            val expectedQuantity = Quantity(3)

            then("it should update the ui state with the correct values") {
                verifySequence {
                    observer.onChanged(any())
                    observer.onChanged(RefrigeratorViewModel.UIState.Updated(updatedStockItem))
                    updatedStockItem.quantity shouldBe expectedQuantity
                }
            }
        }
    }
})