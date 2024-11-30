package lotto.controller

import lotto.domain.Ticket
import lotto.domain.WinningNumbers
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse
import lotto.repository.TicketRepository
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
class LottoController(
    private val ticketRepository: TicketRepository,
) {

    @GetMapping("/api/tickets/{id}")
    @Transactional(readOnly = true)
    fun getTicket(@PathVariable("id") id: Long): TicketResponse {
        val ticket = ticketRepository.findById(id).orElseThrow()
        return TicketResponse.of(id, ticket.lottos)
    }

    @PostMapping("/api/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun postTicket(@RequestBody request: TicketRequest): TicketResponse {
        val ticket = Ticket(request.numbers)
        val savedTicket = ticketRepository.save(ticket)
        return TicketResponse.of(ticket.id, savedTicket.lottos)
    }

    @GetMapping("/api/count/{id}")
    @Transactional(readOnly = true)
    fun countWinningLotto(
        @PathVariable("id") id: Long,
        @RequestParam winningNumber: List<Int>,
        @RequestParam bonusNumber: Int
    ): WinningResultResponse {
        val winningNumbers = WinningNumbers(winningNumber, bonusNumber)
        val ticket = ticketRepository.findById(id).orElseThrow()
        val result = winningNumbers.findRank(ticket)
        return WinningResultResponse(result)
    }
}
