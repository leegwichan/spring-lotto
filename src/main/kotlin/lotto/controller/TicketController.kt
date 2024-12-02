package lotto.controller

import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse
import lotto.service.TicketService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class TicketController(
    private val ticketService: TicketService,
) {

    @GetMapping("/api/tickets/{id}")
    fun getTicket(@PathVariable("id") id: Long): TicketResponse =
        ticketService.getTicket(id)

    @PostMapping("/api/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTicket(@RequestBody request: TicketRequest): TicketResponse =
        ticketService.createTicket(request)

    @GetMapping("/api/count/{id}")
    fun countWinningLotto(
        @PathVariable("id") id: Long,
        @RequestParam winningNumber: List<Int>,
        @RequestParam bonusNumber: Int
    ): WinningResultResponse =
        ticketService.getWinningResult(id, winningNumber, bonusNumber)
}
