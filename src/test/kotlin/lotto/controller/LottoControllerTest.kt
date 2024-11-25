package lotto.controller

import io.kotest.matchers.shouldBe
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import lotto.domain.Ticket
import lotto.domain.WinningRank
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse

class LottoControllerTest : BaseControllerTest() {

    companion object {
        private val NUMBERS = listOf(listOf(1, 2, 3, 4, 5, 6), listOf(7, 8, 9, 10, 11, 12))
    }

    @Test
    fun `로또 티켓을 생성할 수 있다`() {
        var request = TicketRequest(NUMBERS)

        var response =
            Given {
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
        val ticket = ticketRepository.save(Ticket(NUMBERS))

        var response =
            When {
                get("/api/tickets/${ticket.id}")
            } Then {
                statusCode(200)
            } Extract {
                `as`(TicketResponse::class.java)
            }

        response.id shouldBe ticket.id
        response.numbers shouldBe NUMBERS
    }

    @Test
    fun `로또 티켓의 당첨 결과를 알 수 있다`() {
        val ticket = ticketRepository.save(Ticket(NUMBERS))
        val winningNumber = listOf(2, 3, 4, 5, 6, 7)
        val bonusNumber = 1

        val response =
            Given {
                queryParam("winningNumber", winningNumber)
                queryParam("bonusNumber", bonusNumber)
            } When {
                get("/api/count/${ticket.id}")
            } Then {
                statusCode(200)
            } Extract {
                `as`(WinningResultResponse::class.java)
            }

        response.result shouldBe mapOf(WinningRank.SECOND to 1, WinningRank.NOTHING to 1)
    }
}
