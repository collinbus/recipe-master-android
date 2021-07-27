package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.Observer
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.persistence.repository.StockItemRepository
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.refrigerator.RefrigeratorViewModel.UIState
import be.collin.recipemaster.stockItemArb
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.arbitrary.next
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
            mutableListOf(
                StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                StockItem(name = secondName, quantity = Quantity(secondQuantity)),
                StockItem(name = thirdName, quantity = Quantity(thirdQuantity)),
            )
        )

        val repository: StockItemRepository = mockk {
            coEvery { getRefrigeratorStockItems() } returns stockItems
        }

        val viewModel = RefrigeratorViewModelImpl(repository)

        `when`("the viewModel is created") {
            val observer: Observer<UIState> = spyk()
            viewModel.uiState.observeForever(observer)

            then("it emit the correct ui state") {
                val expectedUIState = UIState.Initialized(
                    stockItems
                )
                verify { observer.onChanged(expectedUIState) }
            }
        }

        `when`("the quantity of an item is increased") {
            val observer: Observer<UIState> = spyk()
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(2))
            val expectedQuantity = Quantity(3)
            viewModel.uiState.observeForever(observer)

            viewModel.increaseQuantityOf(updatedStockItem)

            then("it should update the ui state with the correct values") {
                verifySequence {
                    observer.onChanged(any())
                    observer.onChanged(UIState.Updated(updatedStockItem))
                    updatedStockItem.quantity shouldBe expectedQuantity
                }
            }
        }

        `when`("the quantity of an item is decreased") {
            val observer: Observer<UIState> = spyk()
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(2))
            val expectedQuantity = Quantity(1)
            viewModel.uiState.observeForever(observer)

            viewModel.decreaseQuantityOf(updatedStockItem)

            then("it should update the ui state with the correct values") {
                verifySequence {
                    observer.onChanged(any())
                    observer.onChanged(UIState.Updated(updatedStockItem))
                    updatedStockItem.quantity shouldBe expectedQuantity
                }
            }
        }

        `when`("the name of an item is changed") {
            val observer: Observer<UIState> = spyk()
            val updatedName = "Tomatos"
            val updatedStockItem = StockItem(name = "Tomato", quantity = Quantity(3))
            viewModel.uiState.observeForever(observer)

            viewModel.changeName(updatedName, updatedStockItem)

            then("it should update it") {
                updatedStockItem.name shouldBe updatedName
            }
        }

        `when`("saveStockItems is called") {
            val observer: Observer<UIState> = spyk()
            viewModel.uiState.observeForever(observer)

            viewModel.saveStockItems()

            then("it should call saveItems on the repository") {
                coVerify { repository.insertRefrigeratorStockItems(stockItems) }
            }
        }

        `when`("addStockItem is called") {
            val observer: Observer<UIState> = spyk()
            viewModel.uiState.observeForever(observer)

            viewModel.addStockItem()

            then("it should update the repository") {
                coVerify { repository.insertRefrigeratorStockItems(any()) }
            }

            then("it should update the uistate") {
                coVerifySequence {
                    observer.onChanged(any())
                    observer.onChanged(ofType(UIState.Added::class))
                }
            }
        }
    }
})