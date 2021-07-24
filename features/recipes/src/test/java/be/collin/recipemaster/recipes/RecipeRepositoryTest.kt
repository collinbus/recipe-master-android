package be.collin.recipemaster.recipes

import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import be.collin.recipemaster.shouldBeLeftWithValue
import be.collin.recipemaster.shouldBeRightWithValue
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.mockk

internal class RecipeRepositoryTest : BehaviorSpec({
    given("a successful RecipeRepository") {
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
            val recipes = repository.getRecipes()

            then("it should return the correct list of recipes") {
                val expectedRecipes = listOf(Recipe(recipeName, recipeDuration, Base64Image(recipeImageData)))
                recipes shouldBeRightWithValue expectedRecipes
            }
        }

        `when`("getRecipes is invoked twice") {
            repository.getRecipes()
            val recipes = repository.getRecipes()

            then("it should return the cached response") {
                val expectedRecipes = listOf(Recipe(recipeName, recipeDuration, Base64Image(recipeImageData)))
                recipes shouldBeRightWithValue expectedRecipes
            }
        }
    }

    given("a RecipeRepository with an unresponsive endpoint") {
        val exception = IllegalStateException()
        val api: RecipeApi = mockk {
            coEvery { getRecipes() } throws exception
        }

        val repository = RecipeRepository(api)

        `when`("getRecipes is called") {
            val recipes = repository.getRecipes()

            then("it should return the received exception") {
                recipes shouldBeLeftWithValue exception
            }
        }
    }

    given("a RecipeRepository with a wrong response structure") {
        val api: RecipeApi = mockk {
            coEvery { getRecipes() } returns JsonObject()
        }

        val repository = RecipeRepository(api)

        `when`("getRecipes is called") {
            val recipes = repository.getRecipes()

            then("it should return the received exception") {
                recipes shouldBeLeftWithValue NullPointerException()
            }
        }
    }

    given("a RecipeRepository with a cached response") {
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
            coEvery { getRecipes() } returnsMany listOf(mockedRecipesResponse, JsonObject())
        }

        val repository = RecipeRepository(api)

        `when`("getRecipes is invoked a second time") {
            repository.getRecipes()
            val recipes = repository.getRecipes()

            then("it should return the cached response") {
                val expectedRecipes = listOf(Recipe(recipeName, recipeDuration, Base64Image(recipeImageData)))
                recipes shouldBeRightWithValue expectedRecipes
            }
        }
    }
})
