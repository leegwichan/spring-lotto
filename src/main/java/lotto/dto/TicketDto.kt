package lotto.dto

import lotto.domain.Lotto

data class TicketRequest(
    val numbers: List<List<Int>>
)

data class TicketResponse(
    val id: Long,
    val numbers: List<List<Int>>,
) {

    companion object {
        fun of(id: Long, lotties: List<Lotto>): TicketResponse {
            return TicketResponse(id, lotties.map { it.numbers })
        }
    }
}
