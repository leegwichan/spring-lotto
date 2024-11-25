package lotto.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class TicketTest : StringSpec({

    "티켓에는 하나 이상의 로또가 있어야 한다" {
        val emptyList: List<List<Int>> = emptyList()

        val expectation = assertThrows<IllegalArgumentException> { Ticket(emptyList) }
        expectation.message shouldBe "티켓은 적어도 한 장 이상의 로또가 있어야 합니다"
    }
})
