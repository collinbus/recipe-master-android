package be.collin.recipemaster.stock

import be.collin.recipemaster.stockItemArb
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.arbitrary.next

internal class StockItemsTest : BehaviorSpec({
    given("5 StockItems") {
        val thirdElement = stockItemArb.next()

        val stockItems = StockItems(listOf(
            stockItemArb.next(),
            stockItemArb.next(),
            thirdElement,
            stockItemArb.next(),
            stockItemArb.next()
        ))

        `when`("size is requested") {
            val size = stockItems.size

            then("it should return 5") {
                size shouldBe 5
            }
        }

        `when`("you want to get the third element") {
            val element = stockItems[2]

            then("it should return the correct element") {
                element shouldBe thirdElement
            }
        }
    }
})