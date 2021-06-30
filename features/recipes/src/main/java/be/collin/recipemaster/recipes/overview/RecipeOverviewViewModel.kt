package be.collin.recipemaster.recipes.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class RecipeOverviewViewModel : ViewModel() {
    abstract val recipes: LiveData<RecipeUIModels>


}