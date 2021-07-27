package be.collin.recipemaster.stock.refrigerator

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import io.kotest.matchers.shouldBe
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class RefrigeratorFragmentTest {

    private class RefrigeratorScreen : Screen<RefrigeratorScreen>() {
        val addBtn = KButton { withId(R.id.addBtn)}
        val refrigerator =
            KRecyclerView({ withId(R.id.stockItemRecyclerView) }, { itemType(::RefrigeratorItem) })

        class RefrigeratorItem(parent: Matcher<View>) : KRecyclerItem<RefrigeratorItem>(parent) {
            val title = KEditText(parent) { withId(R.id.stockItemText) }
            val quantity = KTextView(parent) { withId(R.id.quantityText) }
            val increment = KButton { withId(R.id.incrementFab) }
            val decrement = KButton { withId(R.id.decrementFab) }
        }
    }

    @Test
    fun shouldShowListOfStockItems() {
        val firstName = "Tomato"
        val firstQuantity = 2
        val secondName = "Potato"
        val secondQuantity = 3
        val thirdName = "Milk 1L"
        val thirdQuantity = 1
        launchFragmentInContainerWith(liveData {
            emit(
                RefrigeratorViewModel.UIState.Initialized(
                    StockItems(
                        mutableListOf(
                            StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                            StockItem(name = secondName, quantity = Quantity(secondQuantity)),
                            StockItem(name = thirdName, quantity = Quantity(thirdQuantity)),
                        )
                    )
                ) as RefrigeratorViewModel.UIState
            )
        })

        onScreen<RefrigeratorScreen> {
            refrigerator {
                childAt<RefrigeratorScreen.RefrigeratorItem>(0) {
                    title.hasText(firstName)
                    quantity.hasText("$firstQuantity")
                }

                childAt<RefrigeratorScreen.RefrigeratorItem>(1) {
                    title.hasText(secondName)
                    quantity.hasText("$secondQuantity")
                }

                childAt<RefrigeratorScreen.RefrigeratorItem>(2) {
                    title.hasText(thirdName)
                    quantity.hasText("$thirdQuantity")
                }
            }
        }
    }

    @Test
    fun shouldChangeQuantityCorrectlyWhenIncreasingAndDecreasingIt() {
        val firstName = "Tomato"
        val firstQuantity = 2

        launchFragmentInContainerWith(liveData {
            emit(
                RefrigeratorViewModel.UIState.Initialized(
                    StockItems(
                        mutableListOf(
                            StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                        )
                    )
                ) as RefrigeratorViewModel.UIState
            )
        })

        onScreen<RefrigeratorScreen> {
            refrigerator {
                childAt<RefrigeratorScreen.RefrigeratorItem>(0) {
                    quantity.hasText("$firstQuantity")
                    increment.click()
                    sleep(50)
                    quantity.hasText("${firstQuantity + 1}")
                    decrement.click()
                    sleep(50)
                    quantity.hasText("$firstQuantity")
                }
            }
        }
    }

    @Test
    fun shouldAddItemWhenAddButtonIsClicked() {
        val firstName = "Tomato"
        val firstQuantity = 2

        launchFragmentInContainerWith(liveData {
            emit(
                RefrigeratorViewModel.UIState.Initialized(
                    StockItems(
                        mutableListOf(
                            StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                        )
                    )
                ) as RefrigeratorViewModel.UIState
            )
        })

        onScreen<RefrigeratorScreen> {
            addBtn.click()

            refrigerator {
                childAt<RefrigeratorScreen.RefrigeratorItem>(1) {
                    title.hasText("New item")
                    quantity.hasText("1")
                }
            }
        }
    }

    @Test
    fun shouldRemoveItemWhenItIsSwipedToTheLeft() {
        val firstName = "Tomato"
        val firstQuantity = 2

        launchFragmentInContainerWith(liveData {
            emit(
                RefrigeratorViewModel.UIState.Initialized(
                    StockItems(
                        mutableListOf(
                            StockItem(name = firstName, quantity = Quantity(firstQuantity)),
                        )
                    )
                ) as RefrigeratorViewModel.UIState
            )
        })

        onScreen<RefrigeratorScreen> {
            refrigerator {
                childAt<RefrigeratorScreen.RefrigeratorItem>(0) {
                    view.perform(GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_RIGHT,GeneralLocation.CENTER_LEFT, Press.FINGER))
                }
            }

            refrigerator.getSize() shouldBe 0
        }
    }

    private fun launchFragmentInContainerWith(
        fridgeItemsLiveData: LiveData<RefrigeratorViewModel.UIState> = liveData { }
    ) {
        stopKoin()
        startKoin {
            modules(module {
                viewModel<RefrigeratorViewModel> {
                    object : RefrigeratorViewModel() {
                        val uiStateMediatorLiveData = MediatorLiveData<UIState>().apply {
                            addSource(fridgeItemsLiveData) { value = it }
                        }

                        override fun increaseQuantityOf(stockItem: StockItem) {
                            stockItem.quantity.increment()
                            this.uiStateMediatorLiveData.postValue(UIState.Updated(stockItem))
                        }

                        override fun decreaseQuantityOf(stockItem: StockItem) {
                            stockItem.quantity.decrement()
                            this.uiStateMediatorLiveData.postValue(UIState.Updated(stockItem))
                        }

                        override fun changeName(newName: String, stockItem: StockItem) {}
                        override fun saveStockItems() {}
                        override fun addStockItem() {
                            uiStateMediatorLiveData.postValue(UIState.Added(StockItem(name = "New item", quantity = Quantity(1))))
                        }

                        override fun removeItemAt(position: Int) {
                            uiStateMediatorLiveData.postValue(UIState.Removed(0))
                        }

                        override val uiState: LiveData<UIState>
                            get() = this.uiStateMediatorLiveData
                    }
                }
            })
        }
        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            RefrigeratorFragment()
        }
    }
}