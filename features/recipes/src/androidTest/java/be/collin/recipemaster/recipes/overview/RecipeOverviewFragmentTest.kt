package be.collin.recipemaster.recipes.overview

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.recipes.R
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KTextView
import io.kotest.matchers.shouldBe
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class RecipeOverviewFragmentTest {

    private class RecipeOverviewScreen : Screen<RecipeOverviewScreen>() {
        val recipes = KRecyclerView({ withId(R.id.recipesList) }, { itemType(::RecipeItem) })

        class RecipeItem(parent: Matcher<View>) : KRecyclerItem<RecipeItem>(parent) {
            val title = KTextView(parent) { withId(R.id.recipeTitle)  }
            val duration = KTextView(parent) { withId(R.id.recipeDuration) }
            val image = KTextView(parent) { withId(R.id.recipeImage) }
        }
    }

    @Test
    fun shouldShowListOfRecipesWhenScreenLoaded() {
        stopKoin()
        startKoin {
            modules(module {
                viewModel<RecipeOverviewViewModel> {
                    object : RecipeOverviewViewModel() {
                        override val recipes: LiveData<RecipeUIModels> get() = liveData {
                            emit(RecipeUIModels(listOf(
                                RecipeUIModel(Recipe("First", 15, Base64Image(""))),
                                RecipeUIModel(Recipe("Second", 1, Base64Image(""))),
                                RecipeUIModel(Recipe("Third", 50, Base64Image(""))),
                            )))
                        }
                    }
                }
            })
        }

        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            RecipeOverviewFragment()
        }

        onScreen<RecipeOverviewScreen> {
            recipes {
                getSize() shouldBe 3
                childAt<RecipeOverviewScreen.RecipeItem>(0) {
                    title.hasText("First")
                    duration.hasText("15 mins")
                    image.isDisplayed()
                }
                childAt<RecipeOverviewScreen.RecipeItem>(1) {
                    title.hasText("Second")
                    duration.hasText("1 min")
                    image.isDisplayed()
                }
                childAt<RecipeOverviewScreen.RecipeItem>(2) {
                    title.hasText("Third")
                    duration.hasText("50 mins")
                    image.isDisplayed()
                }
            }
        }
    }
}