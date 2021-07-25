package be.collin.recipemaster.recipes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class RecipeDetailsViewModel : ViewModel() {
    abstract val recipeDetails: LiveData<RecipeDetailsUIModel>
}