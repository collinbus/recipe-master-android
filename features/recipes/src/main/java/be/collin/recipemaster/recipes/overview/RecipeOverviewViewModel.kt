package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import be.collin.recipemaster.recipes.RecipeRepository
import kotlinx.coroutines.flow.collect

abstract class RecipeOverviewViewModel : ViewModel() {
    abstract val recipes: LiveData<RecipeUIModels>
}

class RecipeOverviewViewModelImpl(
    private val recipeRepository: RecipeRepository
) : RecipeOverviewViewModel() {

    override val recipes: LiveData<RecipeUIModels> = liveData {
        recipeRepository.getRecipes().fold(::handleError, { recipes ->
            val uiModels = recipes.map { recipe ->
                RecipeUIModel(recipe)
            }
            emit(RecipeUIModels(uiModels))
        })
    }

    fun handleError(throwable: Throwable) {

    }
}
