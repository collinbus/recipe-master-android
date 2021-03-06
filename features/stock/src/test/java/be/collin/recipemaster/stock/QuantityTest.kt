package be.collin.recipemaster.stock

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class QuantityTest : StringSpec({
    "should return correct value when Quantity value is requested" {
        val value = 5

        val quantity = Quantity(value)

        quantity.value shouldBe value
    }

    "should return correct value when quantity is incremented" {
        val value = 5
        val expectedValue = 6
        val quantity = Quantity(value)

        quantity.increment()

        quantity.value shouldBe expectedValue
    }

    "should return correct value when quantity is decremented" {
        val value = 5
        val expectedValue = 4
        val quantity = Quantity(value)

        quantity.decrement()

        quantity.value shouldBe expectedValue
    }

    "should not decrement value if decrementing at value 0" {
        val value = 0
        val expectedValue = 0
        val quantity = Quantity(value)

        quantity.decrement()

        quantity.value shouldBe 0
    }
})