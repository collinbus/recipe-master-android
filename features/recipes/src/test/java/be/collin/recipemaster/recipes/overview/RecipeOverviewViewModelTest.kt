package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.Observer
import be.collin.recipemaster.recipes.RecipeRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.property.arbitrary.next
import io.mockk.*
import kotlinx.coroutines.flow.flow

internal class RecipeOverviewViewModelImplTest : BehaviorSpec({
    val firstRecipe = recipeArb.next()
    val secondRecipe = recipeArb.next()
    val thirdRecipe = recipeArb.next()
    val recipeRepositoryMock: RecipeRepository = mockk {
        coEvery { getRecipes() } returns flow { emit(listOf(firstRecipe,secondRecipe,thirdRecipe)) }
    }

    given("a RecipeOverviewViewModel") {
        val observer: Observer<RecipeUIModels> = spyk()
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
                verify { observer.onChanged(expectedUIValues) }
            }
        }
    }
})