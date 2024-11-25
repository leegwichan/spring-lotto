package lotto.controller

import lotto.domain.Ticket
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import io.kotest.matchers.shouldBe
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When

private val NUMBERS = listOf(listOf(1, 2, 3, 4, 5, 6), listOf(7, 8, 9, 10, 11, 12))

class LottoControllerTest : BaseControllerTest() {

    @Test
    fun `로또 티켓을 생성할 수 있다`() {
        var request = TicketRequest(NUMBERS)

        var response = Given {
            contentType(ContentType.JSON)
            body(request)
        } When {
            post("/api/tickets")
        } Then {
            statusCode(201)
        } Extract {
            `as`(TicketResponse::class.java)
        }

        response.numbers shouldBe NUMBERS
    }

    @Test
    fun `로또 티켓을 조회할 수 있다`() {
        val ticket = Ticket(NUMBERS)
        val savedTicket = ticketRepository.save(ticket)

        var response = When {
            get("/api/tickets/${savedTicket.id}")
        } Then {
            statusCode(200)
        } Extract {
            `as`(TicketResponse::class.java)
        }

        response.id shouldBe savedTicket.id
        response.numbers shouldBe NUMBERS
    }
}
