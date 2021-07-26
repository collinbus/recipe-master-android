package be.collin.recipemaster.stock.refrigerator

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class RefrigeratorFragmentTest {

    private class RefrigeratorScreen : Screen<RefrigeratorScreen>() {
        val refrigerator =
            KRecyclerView({ withId(R.id.stockItemRecyclerView) }, { itemType(::RefrigeratorItem) })

        class RefrigeratorItem(parent: Matcher<View>) : KRecyclerItem<RefrigeratorItem>(parent) {
            val title = KEditText(parent) { withId(R.id.stockItemText) }
            val quantity = KTextView(parent) { withId(R.id.quantityText) }
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
                StockItems(
                    listOf(
                        StockItem(firstName, Quantity(firstQuantity)),
                        StockItem(secondName, Quantity(secondQuantity)),
                        StockItem(thirdName, Quantity(thirdQuantity)),
                    )
                )
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

    private fun launchFragmentInContainerWith(
        fridgeItemsLiveData: LiveData<StockItems> = liveData { }
    ) {
        stopKoin()
        startKoin {
            modules(module {
                viewModel<RefrigeratorViewModel> {
                    object : RefrigeratorViewModel() {
                        override val fridgeItems: LiveData<StockItems>
                            get() = fridgeItemsLiveData
                    }
                }
            })
        }
        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            RefrigeratorFragment()
        }
    }
}