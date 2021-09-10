package be.collin.recipemaster.recipes

import be.collin.recipemaster.recipes.overview.Recipes
import be.collin.recipemaster.recipe
import be.collin.recipemaster.shouldBeLeftWithValue
import be.collin.recipemaster.shouldBeRightWithValue
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next

class RecipesTest : StringSpec({
    "recipeWithName should return correct recipe" {
        val first = Arb.recipe().next().copy("first")
        val second = Arb.recipe().next().copy("second")
        val third = Arb.recipe().next().copy("third")
        val recipes = Recipes(
            listOf(
                first,
                second,
                third,
            )
        )

        val recipe = recipes.recipeWithName("second")

        recipe shouldBeRightWithValue second
    }

    "recipeWithName should throw exception if recipe does not exist" {
        val first = Arb.recipe().next().copy("first")
        val second = Arb.recipe().next().copy("second")
        val third = Arb.recipe().next().copy("third")
        val recipes = Recipes(
            listOf(
                first,
                second,
                third,
            )
        )

        recipes.recipeWithName("fourth") shouldBeLeftWithValue NoSuchElementException("Collection contains no element matching the predicate.")
    }
})