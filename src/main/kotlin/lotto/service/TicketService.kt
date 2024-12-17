package lotto.service

import lotto.domain.Ticket
import lotto.domain.WinningNumbers
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse
import lotto.repository.TicketRepository
import lotto.repository.getById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(private val ticketRepository: TicketRepository) {
    @Transactional(readOnly = true)
    fun getTicket(id: Long): TicketResponse {
        val ticket = ticketRepository.getById(id)
        return TicketResponse.of(id, ticket.lottos)
    }

    @Transactional
    fun createTicket(request: TicketRequest): TicketResponse {
        val ticket = Ticket(request.numbers)
        val savedTicket = ticketRepository.save(ticket)
        return TicketResponse.of(ticket.id, savedTicket.lottos)
    }

    @Transactional(readOnly = true)
    fun getWinningResult(
        id: Long,
        winningNumber: List<Int>,
        bonusNumber: Int,
    ): WinningResultResponse {
        val winningNumbers = WinningNumbers(winningNumber, bonusNumber)
        val ticket = ticketRepository.getById(id)
        val result = winningNumbers.findRank(ticket)
        return WinningResultResponse.from(result)
    }
}
