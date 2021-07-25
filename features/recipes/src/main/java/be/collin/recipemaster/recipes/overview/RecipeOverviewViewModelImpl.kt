package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.*
import be.collin.recipemaster.recipes.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeOverviewViewModelImpl(
    private val recipeRepository: RecipeRepository
) : RecipeOverviewViewModel() {

    private val initialState = liveData {
        emit(UIState.Loading)
        recipeRepository.getRecipes().fold({
            emit(UIState.Error)
        }, { recipes ->
            emit(UIState.Success(recipes.toUIModels()))
        })
    }

    private val currentState = MutableLiveData<UIState>()

    override val selectedRecipe: LiveData<Recipe>
        get() = TODO("Not yet implemented")

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(currentState) { value = it }
        addSource(initialState) { value = it }
    }

    override fun refreshRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipes().fold({
                currentState.postValue(UIState.Error)
            }, { recipes ->
                currentState.postValue(UIState.Success(recipes.toUIModels()))
            })
        }
    }

    override fun onRecipeClicked(title: String) {
        TODO("Not yet implemented")
    }

    private fun List<Recipe>.toUIModels(): RecipeUIModels = RecipeUIModels(map { recipe ->
        RecipeUIModel(recipe)
    })
}
