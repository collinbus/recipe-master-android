package be.collin.recipemaster.recipes

import arrow.core.Either
import arrow.core.computations.either
import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository(
    private val api: RecipeApi
) {
    suspend fun getRecipes(): Either<Throwable,List<Recipe>> = Either.catch {
        val recipes = api.getRecipes().get("recipes").asJsonArray.map {
            val recipeObj = it.asJsonObject
            val name = recipeObj.get("name").asString
            val duration = recipeObj.get("durationInMins").asInt
            val imageData = recipeObj.get("base64Image").asJsonObject.get("data").asString
            Recipe(name, duration, Base64Image(imageData))
        }
        recipes
    }
}