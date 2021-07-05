package be.collin.recipemaster

import arrow.core.Either
import arrow.core.getOrElse
import io.kotest.assertions.fail
import io.kotest.matchers.shouldBe

infix fun <A, B> Either<A, B>.shouldBeRightWithValue(expectedValue: B) {
    this.getOrElse { fail("Either is a left instead of a right") } shouldBe expectedValue
}