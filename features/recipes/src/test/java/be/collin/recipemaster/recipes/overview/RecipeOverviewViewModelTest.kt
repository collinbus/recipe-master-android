package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.Observer
import arrow.core.left
import arrow.core.right
import be.collin.recipemaster.recipe
import be.collin.recipemaster.recipes.RecipeRepository
import be.collin.recipemaster.recipes.overview.RecipeOverviewViewModel.UIState.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.mockk.*

internal class RecipeOverviewViewModelImplTest : BehaviorSpec({

    given("a RecipeOverviewViewModel") {
        val firstRecipe = Arb.recipe().next()
        val secondRecipe = Arb.recipe().next()
        val thirdRecipe = Arb.recipe().next()
        val recipeRepositoryMock: RecipeRepository = mockk {
            coEvery { getRecipes() } returns listOf(firstRecipe,secondRecipe,thirdRecipe).right()
        }

        `when`("the recipes are observed") {
            val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)

            val observer: Observer<RecipeOverviewViewModel.UIState> = spyk()
            viewModel.uiState.observeForever(observer)

            then("it should emit the correct recipe ui values") {
                val expectedUIValues = RecipeUIModels(
                    listOf(
                        RecipeUIModel(firstRecipe),
                        RecipeUIModel(secondRecipe),
                        RecipeUIModel(thirdRecipe),
                    )
                )
                verifySequence {
                    observer.onChanged(Loading)
                    observer.onChanged(Success(expectedUIValues))
                }
            }
        }

        `when`("a recipe is clicked") {
            val observer: Observer<Recipe> = spyk()
            val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)
            viewModel.uiState.observeForever(spyk())
            viewModel.selectedRecipe.observeForever(observer)

            viewModel.onRecipeClicked(secondRecipe.name)

            then("it should update the selected recipe with the correct value") {
                verify { observer.onChanged(secondRecipe) }
            }
        }

        `when`("the recipes are refreshing") {
            val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)

            val observer: Observer<RecipeOverviewViewModel.UIState> = spyk()
            viewModel.uiState.observeForever(observer)
            viewModel.refreshRecipes()

            then("it should emit the correct recipe ui values") {
                val expectedUIValues = RecipeUIModels(
                    listOf(
                        RecipeUIModel(firstRecipe),
                        RecipeUIModel(secondRecipe),
                        RecipeUIModel(thirdRecipe),
                    )
                )
                verifySequence {
                    observer.onChanged(Loading)
                    observer.onChanged(Success(expectedUIValues))
                    observer.onChanged(Success(expectedUIValues))
                }
            }
        }
    }

    given("a RecipeOverviewViewModel that fails") {
        val recipeRepositoryMock: RecipeRepository = mockk {
            coEvery { getRecipes() } returns IllegalArgumentException().left()
        }

        val observer: Observer<RecipeOverviewViewModel.UIState> = spyk()
        val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)

        `when`("the recipes are observed") {
            viewModel.uiState.observeForever(observer)

            then("it should emit the correct error value") {
                val expectedState = Error
                verify { observer.onChanged(expectedState) }
            }
        }
    }
})