package be.collin.recipemaster.recipes

import be.collin.recipemaster.recipes.overview.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository {
    fun getRecipes(): Flow<List<Recipe>> {
        TODO()
    }
}