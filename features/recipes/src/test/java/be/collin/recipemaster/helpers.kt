package be.collin.recipemaster

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import io.kotest.assertions.fail
import io.kotest.matchers.shouldBe

infix fun <A, B> Either<A, B>.shouldBeRightWithValue(expectedValue: B) {
    this.getOrElse { fail("Either is a left instead of a right") } shouldBe expectedValue
}

infix fun <A, B> Either<A, B>.shouldBeLeftWithValue(expectedValue: A) {
    this.fold({
        it shouldBe expectedValue
    }, {
        fail("Either is a right instead of a left")
    })
}