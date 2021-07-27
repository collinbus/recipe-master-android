package be.collin.recipemaster

import be.collin.recipemaster.stock.Quantity
import be.collin.recipemaster.stock.StockItem
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

val stockItemArb = arbitrary {
    StockItem(name = Arb.string().next(), quantity = quantityArb.next())
}

val quantityArb = arbitrary {
    Quantity(Arb.int(IntRange(0, 99)).next())
}