package lotto.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class WinningRankTest : StringSpec({

    table(
        headers("matchingCount", "isBonusMatch", "expected"),
        row(6, false, WinningRank.FIRST),
        row(5, true, WinningRank.SECOND),
        row(5, false, WinningRank.THIRD),
        row(4, true, WinningRank.FOURTH),
        row(4, false, WinningRank.FOURTH),
        row(3, true, WinningRank.FIFTH),
        row(3, false, WinningRank.FIFTH),
        row(2, true, WinningRank.NOTHING),
        row(0, false, WinningRank.NOTHING),
    ).forAll { matchingCount, isBonusMatch, expected ->

        "당첨 번호가 $matchingCount 개 일치하고 보너스 번호가 $isBonusMatch 이면 $expected 순위이다" {
            WinningRank.findRank(matchingCount, isBonusMatch) shouldBe expected
        }
    }
})
