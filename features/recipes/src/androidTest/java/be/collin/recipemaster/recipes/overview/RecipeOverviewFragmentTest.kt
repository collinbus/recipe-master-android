package be.collin.recipemaster.recipes.overview

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.recipes.R
import be.collin.recipemaster.recipes.overview.RecipeOverviewViewModel.UIState.Loading
import com.agoda.kakao.dialog.KAlertDialog
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
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
        val swipeRefresh = KSwipeRefreshLayout { withId(R.id.swipeRefreshRecipes) }
        val errorDialog = KAlertDialog()

        class RecipeItem(parent: Matcher<View>) : KRecyclerItem<RecipeItem>(parent) {
            val title = KTextView(parent) { withId(R.id.recipeTitle) }
            val duration = KTextView(parent) { withId(R.id.recipeDuration) }
            val image = KTextView(parent) { withId(R.id.recipeImage) }
        }
    }

    private val navigationController =
        TestNavHostController(ApplicationProvider.getApplicationContext())

    @Test
    fun shouldShowListOfRecipesWhenScreenLoaded() {
        val data = "R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
        launchRecipeOverviewFragmentInContainerWith(uiStateLiveData =
        liveData<RecipeOverviewViewModel.UIState> {
            emit(
                RecipeOverviewViewModel.UIState.Success(
                    RecipeUIModels(
                        listOf(
                            RecipeUIModel(Recipe("First", 15, Base64Image(data))),
                            RecipeUIModel(Recipe("Second", 1, Base64Image(data))),
                            RecipeUIModel(Recipe("Third", 50, Base64Image(data))),
                        )
                    )
                )
            )
        })

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

    @Test
    fun shouldShowLoadingIndicatorWhenLoadingStateIsObserved() {
        launchRecipeOverviewFragmentInContainerWith(
            uiStateLiveData = liveData<RecipeOverviewViewModel.UIState> {
                emit(Loading)
            }
        )

        onScreen<RecipeOverviewScreen> {
            swipeRefresh {
                isRefreshing()
            }
        }
    }

    @Test
    fun shouldShowLErrorWhenLoadingStateIsObserved() {
        launchRecipeOverviewFragmentInContainerWith(
            uiStateLiveData = liveData<RecipeOverviewViewModel.UIState> {
                emit(RecipeOverviewViewModel.UIState.Error)
            }
        )

        onScreen<RecipeOverviewScreen> {
            errorDialog {
                isDisplayed()
                title.hasText(R.string.error_title)
                message.hasText(R.string.generic_error_description)
                positiveButton.hasText(R.string.ok)
                positiveButton.click()
            }
        }
    }

    @Test
    fun shouldNavigateToDetailsScreenWhenRecipeIsSelected() {
        val value = Recipe("a recipe", 12, Base64Image(""))
        launchRecipeOverviewFragmentInContainerWith(
            selectedRecipeLiveData = liveData { emit(value) }
        )

        onScreen<RecipeOverviewScreen> {
            navigationController.currentDestination!!.id shouldBe R.id.recipeDetailsFragment
        }
    }

    private fun launchRecipeOverviewFragmentInContainerWith(
        uiStateLiveData: LiveData<RecipeOverviewViewModel.UIState> = liveData { },
        selectedRecipeLiveData: LiveData<Recipe> = liveData { }
    ) {
        val uiStateMediatorLiveData = MediatorLiveData<RecipeOverviewViewModel.UIState>().apply {
            addSource(uiStateLiveData) { value = it }
        }
        stopKoin()
        startKoin {
            modules(module {
                viewModel<RecipeOverviewViewModel> {
                    object : RecipeOverviewViewModel() {
                        override val selectedRecipe: LiveData<Recipe>
                            get() = selectedRecipeLiveData
                        override val uiState: MediatorLiveData<UIState>
                            get() = uiStateMediatorLiveData

                        override fun refreshRecipes() {}
                        override fun onRecipeClicked(title: String) {}
                    }
                }
            })
        }

        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            RecipeOverviewFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    it?.let {
                        navigationController.setGraph(R.navigation.recipes_graph)
                        navigationController.setCurrentDestination(R.id.recipeOverviewFragment)
                        Navigation.setViewNavController(
                            fragment.requireView(),
                            navigationController
                        )
                    }
                }
            }
        }
    }
}