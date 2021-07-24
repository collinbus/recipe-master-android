package be.collin.recipemaster.recipes

import arrow.core.Either
import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe

class RecipeRepository(
    private val api: RecipeApi
) {
    private var cachedRecipes: List<Recipe>? = null

    suspend fun getRecipes(): Either<Throwable,List<Recipe>> = Either.catch {
        if (cachedRecipes == null) {
            cachedRecipes = fetchRecipes()
        }
        cachedRecipes!!
    }

    private suspend fun fetchRecipes(): List<Recipe> =
        api.getRecipes().get("recipes").asJsonArray.map {
            val recipeObj = it.asJsonObject
            val name = recipeObj.get("name").asString
            val duration = recipeObj.get("durationInMins").asInt
            val imageData = recipeObj.get("base64Image").asJsonObject.get("data").asString
            Recipe(name, duration, Base64Image(imageData))
        }
}