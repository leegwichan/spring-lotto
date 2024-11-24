package lotto.controller

import jakarta.transaction.Transactional
import lotto.domain.Lotto
import lotto.domain.Ticket
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.repository.LottoRepository
import lotto.repository.TicketRepository
import org.springframework.web.bind.annotation.*

@RestController
class LottoController(
    private val lottoRepository: LottoRepository,
    private val ticketRepository: TicketRepository,
) {

    @GetMapping("/api/tickets/{id}")
    @Transactional
    fun getTicket(@PathVariable("id") id: Long): TicketResponse {
        val ticket = ticketRepository.findById(id).orElseThrow()
        val lotties = lottoRepository.findAllByTicket(ticket)
        return TicketResponse.of(id, lotties)
    }

    @PostMapping("/api/tickets")
    @Transactional
    fun postTicket(@RequestBody request: TicketRequest): TicketResponse {
        val ticket = ticketRepository.save(Ticket())
        val lotties = lottoRepository.saveAll(request.numbers.map { Lotto(it, ticket) })
        return TicketResponse.of(ticket.id, lotties)
    }

    @GetMapping("/api/count/{id}")
    fun countWinningLotto(@PathVariable("id") id: Int): Int {
        return 0
    }
}
