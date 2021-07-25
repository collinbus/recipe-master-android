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
            _recipes = Recipes(recipes)
            emit(UIState.Success(recipes.toUIModels()))
        })
    }
    private var _recipes: Recipes = Recipes(emptyList())

    private val currentState = MutableLiveData<UIState>()
    private val _selectedRecipe = MutableLiveData<Recipe?>()

    override val selectedRecipe = MediatorLiveData<Recipe>().apply {
        addSource(_selectedRecipe) { value = it }
    }

    override val uiState = MediatorLiveData<UIState>().apply {
        addSource(currentState) { value = it }
        addSource(initialState) { value = it }
    }

    override fun refreshRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipes().fold({
                currentState.postValue(UIState.Error)
            }, { recipes ->
                _recipes = Recipes(recipes)
                currentState.postValue(UIState.Success(recipes.toUIModels()))
            })
        }
    }

    override fun onRecipeClicked(name: String) {
        _recipes.recipeWithName(name).fold({},{ recipe ->
            _selectedRecipe.value = recipe
        })
    }

    override fun resetSelectedRecipe() {
        _selectedRecipe.value = null
    }

    private fun List<Recipe>.toUIModels(): RecipeUIModels = RecipeUIModels(map { recipe ->
        RecipeUIModel(recipe)
    })
}
