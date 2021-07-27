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

        `when`("The third element is requested") {
            val element = stockItems[2]

            then("it should return the correct element") {
                element shouldBe thirdElement
            }
        }

        `when`("The index of the third element is requested") {
            val index = stockItems.indexOf(thirdElement)

            then("it should return 2") {
                index shouldBe 2
            }
        }

        `when`("The index of an unExisting element") {
            val index = stockItems.indexOf(StockItem("bla", Quantity(0)))

            then("it should return -1") {
                index shouldBe -1
            }
        }

        `when`("Updating a stockItem that exists") {
            val expectedQuantity = Quantity(5)
            val element = thirdElement.copy(quantity = expectedQuantity)

            stockItems.update(element)

            then("It should update that item") {
                stockItems[2].quantity shouldBe expectedQuantity
            }
        }
    }
})