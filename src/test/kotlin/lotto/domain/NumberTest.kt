package lotto.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NumberTest : StringSpec({

    listOf(0, 46).forEach { value ->
        "숫자는 특정 범위 밖이면 예외를 던진다 : $value" {
            val expectation = shouldThrow<IllegalArgumentException> { Number(value) }
            expectation.message shouldBe "각 숫자는 1와 45 사이 이어야 합니다"
        }
    }

    listOf(1, 45).forEach { value ->
        "숫자는 특정 범위 안이어야 생성할 수 있다 : $value" {
            shouldNotThrowAny { Number(value) }
        }
    }
})
