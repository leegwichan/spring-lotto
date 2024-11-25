package lotto.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class WinningNumbersTest : StringSpec({

    val winningNumbers = WinningNumbers(listOf(2, 3, 4, 5, 6, 7), 1)
    val secondPriceLotto = Lotto(listOf(1, 2, 3, 4, 5, 6))
    val noPriceLotto = Lotto(listOf(7, 8, 9, 10, 11, 12))

    "한 티켓에 대해 당첨 결과를 알 수 있다" {
        val ticket = Ticket(listOf(secondPriceLotto, secondPriceLotto, noPriceLotto))
        val expected = mapOf(WinningRank.SECOND to 2, WinningRank.NOTHING to 1)

        val actual = winningNumbers.findRank(ticket)

        actual shouldBe expected
    }
})
