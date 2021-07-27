package be.collin.recipemaster.stock

import be.collin.recipemaster.stockItemArb
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.arbitrary.next

class StockItemTest: StringSpec( {
    "should update the stockItem if update is called" {
        val element = stockItemArb.next()
        val updatedElement = stockItemArb.next()

        element.update(updatedElement)

        element.name shouldBe updatedElement.name
        element.quantity shouldBe updatedElement.quantity
    }
})