package be.collin.recipemaster.recipes

import com.google.gson.JsonObject
import retrofit2.http.GET

interface RecipeApi {
    @GET("/recipes")
    suspend fun getRecipes(): JsonObject
}