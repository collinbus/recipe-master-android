package be.collin.recipemaster.stock

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class QuantityTest : StringSpec({
    "should return correct value when Quantity value is requested" {
        val value = 5

        val quantity = Quantity(value)

        quantity.value shouldBe value
    }
})