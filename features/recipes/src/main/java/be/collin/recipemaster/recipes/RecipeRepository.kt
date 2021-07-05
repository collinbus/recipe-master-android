package be.collin.recipemaster.recipes

import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository(
    private val api: RecipeApi
) {
    suspend fun getRecipes(): Flow<List<Recipe>> = flow {
        val recipes = api.getRecipes().get("recipes").asJsonArray.map {
            val recipeObj = it.asJsonObject
            val name = recipeObj.get("name").asString
            val duration = recipeObj.get("durationInMins").asInt
            val imageData = recipeObj.get("base64Image").asJsonObject.get("data").asString
            Recipe(name, duration, Base64Image(imageData))
        }
        emit(recipes)
    }
}