package domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NumbersTest : StringSpec({

    "숫자의 개수가 정해진 값과 다르다면 예외를 던진다" {
        val integers = listOf(1, 2, 3, 4, 5, 6, 7)
        val expectation = shouldThrow<IllegalArgumentException> { Numbers.from(integers) }
        expectation.message shouldBe "로또 번호는 6개 이어야 합니다"
    }

    "중복된 숫자가 있을 경우 예외를 던진다" {
        val integers = listOf(1, 2, 3, 4, 5, 5)
        val expectation = shouldThrow<IllegalArgumentException> { Numbers.from(integers) }
        expectation.message shouldBe "로또 번호는 중복되어서는 안됩니다"
    }
})
