package be.collin.recipemaster

import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

fun Arb.Companion.recipe(): Arb<Recipe> = arbitrary {
    Recipe(Arb.string().next(it), Arb.int().next(it), Base64Image(Arb.string().next(it)))
}