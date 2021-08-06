package be.collin.recipemaster.recipes.details

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.liveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeDetailsFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayCorrectData() {
        val name = "Pasta pesto"
        val durationInMins = 25
        val expectedDuration = "Duration: $durationInMins mins"
        val recipe = Recipe(
            name,
            durationInMins,
            Base64Image("R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==")
        )

        composeTestRule.setContent {
            val recipeState = liveData {
                emit(
                    RecipeDetailsUIModel(
                        recipe
                    )
                )
            }
            RecipeDetailsScreen(recipeState.observeAsState())
        }

        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedDuration).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("recipe image").assertIsDisplayed()
    }
}