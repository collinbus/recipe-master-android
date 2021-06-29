package be.collin.recipemaster.recipes.overview

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

internal class RecipeUIModelTest : StringSpec({

    table(
        headers("recipe", "expectedTitle", "expectedDuration", "expectedImage"),
        row(Recipe("title", 1, Base64Image("an image")), "title", "1 min", Base64Image("an image")),
        row(Recipe("another title", 15, Base64Image("another image")), "another title", "15 mins", Base64Image("another image")),
    ).forAll { recipe, expectedTitle, expectedDuration, expectedImage ->

        "$recipe's UI model should return correct title, duration and image" {
            val recipeUI = RecipeUIModel(recipe)

            recipeUI.title shouldBe expectedTitle
            recipeUI.image shouldBe expectedImage
            recipeUI.duration shouldBe expectedDuration
        }
    }
})