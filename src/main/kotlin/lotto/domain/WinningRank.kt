package lotto.domain

enum class WinningRank(private val needMatchingCount: Int, private val needBonusMatch: Boolean) {
    FIRST(6, false),
    SECOND(5, true),
    THIRD(5, false),
    FOURTH(4, false),
    FIFTH(3, false),
    NOTHING(0, false),
    ;

    private fun isSatisfyRank(
        matchingCount: Int,
        isBonusMatch: Boolean,
    ): Boolean {
        return isSatisfyMatchingCount(matchingCount) && isSatisfyBonusMatch(isBonusMatch)
    }

    private fun isSatisfyMatchingCount(matchingCount: Int): Boolean {
        return matchingCount >= this.needMatchingCount
    }

    private fun isSatisfyBonusMatch(isBonusMatch: Boolean): Boolean {
        return isBonusMatch || !this.needBonusMatch
    }

    companion object {
        val ORDER_OF_RANK = listOf(FIRST, SECOND, THIRD, FOURTH, FIFTH, NOTHING)

        fun findRank(
            matchingCount: Int,
            isBonusMatch: Boolean,
        ): WinningRank {
            return ORDER_OF_RANK.first { it.isSatisfyRank(matchingCount, isBonusMatch) }
        }
    }
}
