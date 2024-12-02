package lotto.dto

import lotto.domain.Lotto
import lotto.domain.WinningRank

data class TicketRequest(
    val numbers: List<List<Int>>,
)

data class TicketResponse(
    val id: Long,
    val numbers: List<List<Int>>,
) {
    companion object {
        fun of(
            id: Long,
            lotties: List<Lotto>,
        ): TicketResponse {
            return TicketResponse(id, lotties.map { it.numbers.values })
        }
    }
}

data class WinningResultResponse(
    val result: Map<WinningRank, Int>,
) {
    companion object {
        private const val DEFAULT_COUNT = 0

        fun from(totalResult: Map<WinningRank, Int>): WinningResultResponse {
            val result =
                WinningRank.ORDER_OF_RANK
                    .associateWith { totalResult.getOrDefault(it, DEFAULT_COUNT) }
            return WinningResultResponse(result)
        }
    }
}
