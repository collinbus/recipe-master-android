package be.collin.recipemaster.recipes.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import be.collin.recipemaster.recipes.RecipeRepository

class RecipeOverviewViewModelImpl(
    private val recipeRepository: RecipeRepository
) : RecipeOverviewViewModel() {

    override val uiState: LiveData<UIState> = liveData {
        emit(UIState.Loading)
        recipeRepository.getRecipes().fold({
            Log.d("RecipeOverviewViewModel", it.toString())
            emit(UIState.Error)
        }, { recipes ->
            val uiModels = recipes.map { recipe ->
                RecipeUIModel(recipe)
            }
            emit(UIState.Success(RecipeUIModels(uiModels)))
        })
    }
}
