package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.Observer
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItemRepository
import be.collin.recipemaster.stock.StockItems
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

internal class RefrigeratorViewModelTest: BehaviorSpec({
    given("a RefrigeratorViewModel") {
        val firstName = "Tomato"
        val firstQuantity = 2
        val secondName = "Potato"
        val secondQuantity = 3
        val thirdName = "Milk 1L"
        val thirdQuantity = 1
        val stockItems = StockItems(
            listOf(
                StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                StockItem(name = secondName, quantity = Quantity(secondQuantity)),
                StockItem(name = thirdName, quantity = Quantity(thirdQuantity)),
            )
        )

        val repository: StockItemRepository = mockk {
            every { getStockItems() } returns stockItems
        }

        val viewModel = RefrigeratorViewModelImpl(repository)

        `when`("the viewModel is created") {
            val observer: Observer<RefrigeratorViewModel.UIState> = spyk()
            viewModel.uiState.observeForever(observer)

            then("it emit the correct ui state") {
                val expectedUIState = RefrigeratorViewModel.UIState.Initialized(
                    stockItems
                )
                verify { observer.onChanged(expectedUIState) }
            }
        }

        `when`("the quantity of an item is increased") {
            val observer: Observer<RefrigeratorViewModel.UIState> = spyk()
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(2))
            val expectedQuantity = Quantity(3)
            viewModel.uiState.observeForever(observer)

            viewModel.increaseQuantityOf(updatedStockItem)

            then("it should update the ui state with the correct values") {
                verifySequence {
                    observer.onChanged(any())
                    observer.onChanged(RefrigeratorViewModel.UIState.Updated(updatedStockItem))
                    updatedStockItem.quantity shouldBe expectedQuantity
                }
            }
        }

        `when`("the quantity of an item is decreased") {
            val observer: Observer<RefrigeratorViewModel.UIState> = spyk()
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(2))
            val expectedQuantity = Quantity(1)
            viewModel.uiState.observeForever(observer)

            viewModel.decreaseQuantityOf(updatedStockItem)

            then("it should update the ui state with the correct values") {
                verifySequence {
                    observer.onChanged(any())
                    observer.onChanged(RefrigeratorViewModel.UIState.Updated(updatedStockItem))
                    updatedStockItem.quantity shouldBe expectedQuantity
                }
            }
        }

        `when`("the name of an item is changed") {
            val observer: Observer<RefrigeratorViewModel.UIState> = spyk()
            val updatedName = "Tomatos"
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(3))
            viewModel.uiState.observeForever(observer)

            viewModel.changeName(updatedName, updatedStockItem)

            then("it should update it") {
                updatedStockItem.name shouldBe updatedName
            }
        }
    }
})