package be.collin.recipemaster.recipes.details

import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RecipeDetailsUIModelTest : BehaviorSpec({
    given("a RecipeDetailsUIModel") {
        val expectedTitle = "Pasta pesto"
        val durationInMins = 25
        val expectedDuration = "Duration: $durationInMins mins"
        val imageAsString = "123"
        val expectedImageInBytes = byteArrayOf(49, 50, 51)

        `when`("the uiModel is initialized") {
            val recipeDetailsUIModel = RecipeDetailsUIModel(
                Recipe(
                    expectedTitle,
                    durationInMins,
                    Base64Image(imageAsString)
                )
            )

            then("it should contain the correct title") {
                val title = recipeDetailsUIModel.title

                title shouldBe expectedTitle
            }

            then("it should contain the correct image content") {
                val content = recipeDetailsUIModel.imageContent

                content shouldBe expectedImageInBytes
            }

            then("it should contain the correct duration") {
                val duration = recipeDetailsUIModel.duration

                duration shouldBe expectedDuration
            }
        }
    }
})