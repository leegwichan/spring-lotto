package lotto.controller

import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse
import lotto.service.TicketService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController(
    private val ticketService: TicketService,
) {
    @GetMapping("/api/tickets/{id}")
    fun getTicket(
        @PathVariable("id") id: Long,
    ): TicketResponse = ticketService.getTicket(id)

    @PostMapping("/api/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTicket(
        @RequestBody request: TicketRequest,
    ): TicketResponse = ticketService.createTicket(request)

    @GetMapping("/api/count/{id}")
    fun countWinningLotto(
        @PathVariable("id") id: Long,
        @RequestParam winningNumber: List<Int>,
        @RequestParam bonusNumber: Int,
    ): WinningResultResponse = ticketService.getWinningResult(id, winningNumber, bonusNumber)
}
