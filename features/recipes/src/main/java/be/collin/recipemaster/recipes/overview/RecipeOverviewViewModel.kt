package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import be.collin.recipemaster.recipes.RecipeRepository

abstract class RecipeOverviewViewModel : ViewModel() {
    abstract val recipes: LiveData<UIState>

    sealed class UIState {
        data class Success(
            val recipeUIModels: RecipeUIModels
        ) : UIState()

        object Error : UIState()
        object Loading : UIState()
    }
}

class RecipeOverviewViewModelImpl(
    private val recipeRepository: RecipeRepository
) : RecipeOverviewViewModel() {

    override val recipes: LiveData<UIState> = liveData {
        emit(UIState.Loading)
        recipeRepository.getRecipes().fold({
            emit(UIState.Error)
        }, { recipes ->
            val uiModels = recipes.map { recipe ->
                RecipeUIModel(recipe)
            }
            emit(UIState.Success(RecipeUIModels(uiModels)))
        })
    }
}
