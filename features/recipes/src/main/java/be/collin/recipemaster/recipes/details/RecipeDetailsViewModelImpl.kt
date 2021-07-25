package be.collin.recipemaster.recipes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import be.collin.recipemaster.recipes.overview.Recipe
import be.collin.recipemaster.recipes.overview.RecipeUIModel

class RecipeDetailsViewModelImpl(private val recipe: Recipe) : RecipeDetailsViewModel() {
    override val recipeDetails: LiveData<RecipeDetailsUIModel> = liveData {
        emit(RecipeDetailsUIModel(recipe))
    }
}