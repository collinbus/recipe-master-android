package be.collin.recipemaster.recipes

import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first

internal class RecipeRepositoryTest : BehaviorSpec({
    given("a RecipeRepository") {
        val recipeName = "a_name"
        val recipeDuration = 15
        val recipeImageData = "imageData"

        val mockedRecipesResponse = JsonObject().apply {
            add("recipes", JsonArray().apply {
                add(JsonObject().apply {
                    addProperty("name", recipeName)
                    addProperty("durationInMins", recipeDuration)
                    add("base64Image", JsonObject().apply {
                        addProperty("data", recipeImageData)
                    })
                })
            })
        }
        val api: RecipeApi = mockk {
            coEvery { getRecipes() } returns mockedRecipesResponse
        }

        val repository = RecipeRepository(api)

        `when`("getRecipes is called") {
            val recipes = repository.getRecipes().first()

            then("it should return the correct list of recipes") {
                val expectedRecipes = listOf(Recipe(recipeName, recipeDuration, Base64Image(recipeImageData)))
                recipes shouldBe expectedRecipes
            }
        }
    }
})