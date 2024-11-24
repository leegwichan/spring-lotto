package controller

import jakarta.transaction.Transactional
import domain.Ticket
import dto.TicketRequest
import dto.TicketResponse
import repository.TicketRepository
import org.springframework.web.bind.annotation.*

@RestController
class LottoController(
    private val ticketRepository: TicketRepository,
) {

    @GetMapping("/api/tickets/{id}")
    @Transactional
    fun getTicket(@PathVariable("id") id: Long): TicketResponse {
        val ticket = ticketRepository.findById(id).orElseThrow()
        return TicketResponse.of(id, ticket.lottos)
    }

    @PostMapping("/api/tickets")
    @Transactional
    fun postTicket(@RequestBody request: TicketRequest): TicketResponse {
        val ticket = Ticket(request.numbers)
        val savedTicket = ticketRepository.save(ticket)
        return TicketResponse.of(ticket.id, savedTicket.lottos)
    }

    @GetMapping("/api/count/{id}")
    fun countWinningLotto(@PathVariable("id") id: Int): Int {
        return 0
    }
}
