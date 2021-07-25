package be.collin.recipemaster.recipes.details

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.collin.recipemaster.recipes.R
import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KTextView
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class RecipeDetailsFragmentTest {

    private class RecipeDetailsScreen : Screen<RecipeDetailsScreen>() {
        val title = KTextView { withId(R.id.recipeTitle) }
        val image = KImageView { withId(R.id.recipeImage) }
        val duration = KTextView { withId(R.id.recipeDuration) }
    }

    @Test
    fun shouldDisplayCorrectData() {
        val name = "Pasta pesto"
        val durationInMins = 25
        val expectedDuration = "Duration: $durationInMins mins"
        val recipe = Recipe(name, durationInMins, Base64Image("R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="))

        launchFragmentInContainerWith(
            liveData { emit(RecipeDetailsUIModel(recipe)) },
            recipe
        )

        onScreen<RecipeDetailsScreen> {
            title.hasText(name)
            image.isDisplayed()
            duration.hasText(expectedDuration)
        }
    }

    private fun launchFragmentInContainerWith(
        recipeDetailsLiveData: LiveData<RecipeDetailsUIModel>,
        recipe: Recipe
    ) {
        stopKoin()
        startKoin {
            modules(
                module {
                    viewModel<RecipeDetailsViewModel> {
                        object : RecipeDetailsViewModel() {
                            override val recipeDetails: LiveData<RecipeDetailsUIModel>
                                get() = recipeDetailsLiveData
                        }
                    }
                }
            )
        }
        launchFragmentInContainer(
            themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            fragmentArgs = Bundle().apply {
                putParcelable(
                    "recipe", recipe
                )
            }
        ) {
            RecipeDetailsFragment()
        }
    }
}