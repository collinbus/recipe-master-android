package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.Observer
import arrow.core.left
import arrow.core.right
import be.collin.recipemaster.recipes.RecipeRepository
import be.collin.recipemaster.recipes.overview.RecipeOverviewViewModel.UIState.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.property.arbitrary.next
import io.mockk.*

internal class RecipeOverviewViewModelImplTest : BehaviorSpec({

    given("a RecipeOverviewViewModel") {
        val firstRecipe = recipeArb.next()
        val secondRecipe = recipeArb.next()
        val thirdRecipe = recipeArb.next()
        val recipeRepositoryMock: RecipeRepository = mockk {
            coEvery { getRecipes() } returns listOf(firstRecipe,secondRecipe,thirdRecipe).right()
        }

        val observer: Observer<RecipeOverviewViewModel.UIState> = spyk()
        val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)

        `when`("the recipes are observed") {
            viewModel.recipes.observeForever(observer)

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
    }

    given("a RecipeOverviewViewModel that fails") {
        val recipeRepositoryMock: RecipeRepository = mockk {
            coEvery { getRecipes() } returns IllegalArgumentException().left()
        }

        val observer: Observer<RecipeOverviewViewModel.UIState> = spyk()
        val viewModel = RecipeOverviewViewModelImpl(recipeRepositoryMock)

        `when`("the recipes are observed") {
            viewModel.recipes.observeForever(observer)

            then("it should emit the correct error value") {
                val expectedState = Error
                verify { observer.onChanged(expectedState) }
            }
        }
    }
})