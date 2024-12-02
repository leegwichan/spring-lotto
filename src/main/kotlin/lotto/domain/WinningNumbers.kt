package lotto.domain

class WinningNumbers(private val winningNumbers: Numbers, private val bonusNumber: Number) {
    constructor(winningNumbers: List<Int>, bonusNumber: Int) :
        this(Numbers.from(winningNumbers), Number(bonusNumber))

    fun findRank(ticket: Ticket): Map<WinningRank, Int> =
        ticket.lottos
            .groupingBy { findRank(it) }
            .eachCount()

    private fun findRank(lotto: Lotto): WinningRank {
        val matchingNumberCount = lotto.numbers.countMatchingNumbers(winningNumbers)
        val isBonusMatch = lotto.numbers.isContained(bonusNumber)
        return WinningRank.findRank(matchingNumberCount, isBonusMatch)
    }
}
