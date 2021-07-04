package be.collin.recipemaster.recipes.overview

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

val recipeArb = arbitrary {
    Recipe(Arb.string().next(it), Arb.int().next(it), Base64Image(Arb.string().next(it)))
}