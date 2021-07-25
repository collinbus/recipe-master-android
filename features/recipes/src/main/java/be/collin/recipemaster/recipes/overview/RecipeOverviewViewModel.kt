package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

abstract class RecipeOverviewViewModel : ViewModel() {

    abstract val selectedRecipe: LiveData<Recipe>
    abstract val uiState: MediatorLiveData<UIState>
    abstract fun refreshRecipes()
    abstract fun onRecipeClicked(title: String)

    sealed class UIState {
        data class Success(
            val recipeUIModels: RecipeUIModels
        ) : UIState()

        object Error : UIState()
        object Loading : UIState()
    }
}