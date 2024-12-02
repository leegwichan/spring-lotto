package lotto.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import lotto.BaseDocumentTest
import lotto.domain.WinningRank
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.dto.WinningResultResponse
import lotto.service.TicketService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.OBJECT
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(TicketController::class)
class LottoDocumentTest : BaseDocumentTest() {
    @MockkBean
    private lateinit var ticketService: TicketService

    companion object {
        private val NUMBERS = listOf(listOf(1, 2, 3, 4, 5, 6), listOf(7, 8, 9, 10, 11, 12))
    }

    @Test
    fun `로또 티켓을 생성할 수 있다`() {
        val request = TicketRequest(NUMBERS)
        val response = TicketResponse(1L, NUMBERS)
        val document =
            document(
                "ticket/create-ticket",
                requestFields(
                    fieldWithPath("numbers").type(ARRAY).description("티켓 내 로또들"),
                    fieldWithPath("numbers[]").type(ARRAY).description("로또 숫자들"),
                    fieldWithPath("numbers[][]").type(ARRAY).description("숫자"),
                ),
                responseFields(
                    fieldWithPath("id").type(NUMBER).description("티켓 ID"),
                    fieldWithPath("numbers").type(ARRAY).description("티켓 내 로또들"),
                    fieldWithPath("numbers[]").type(ARRAY).description("로또 숫자들"),
                    fieldWithPath("numbers[][]").type(ARRAY).description("숫자"),
                ),
            )

        every { ticketService.createTicket(request) } returns response

        mockMvc.post("/api/tickets") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
        }.andDo {
            handle(document)
        }
    }

    @Test
    fun `로또 티켓을 조회할 수 있다`() {
        val ticketId = 1L
        val response = TicketResponse(1L, NUMBERS)
        val document =
            document(
                "ticket/find-ticket",
                pathParameters(
                    parameterWithName("id").description("티켓 ID"),
                ),
                responseFields(
                    fieldWithPath("id").type(NUMBER).description("티켓 ID"),
                    fieldWithPath("numbers").type(ARRAY).description("티켓 내 로또들"),
                    fieldWithPath("numbers[]").type(ARRAY).description("로또 숫자들"),
                    fieldWithPath("numbers[][]").type(ARRAY).description("숫자"),
                ),
            )

        every { ticketService.getTicket(ticketId) } returns response

        mockMvc.get("/api/tickets/{id}", ticketId)
            .andExpect {
                status { isOk() }
            }.andDo {
                handle(document)
            }
    }

    @Test
    fun `로또 티켓의 당첨 결과를 알 수 있다`() {
        val ticketId = 1L
        val winningNumbers = "2,3,4,5,6,7"
        val bonusNumber = "1"
        val response =
            WinningResultResponse(
                mapOf(
                    WinningRank.FIRST to 0,
                    WinningRank.SECOND to 1,
                    WinningRank.THIRD to 0,
                    WinningRank.FOURTH to 0,
                    WinningRank.FIFTH to 0,
                    WinningRank.NOTHING to 1,
                ),
            )
        val document =
            document(
                "ticket/find-count",
                pathParameters(
                    parameterWithName("id").description("티켓 ID"),
                ),
                queryParameters(
                    parameterWithName("winningNumber").description("당첨 번호"),
                    parameterWithName("bonusNumber").description("보너스 번호"),
                ),
                responseFields(
                    fieldWithPath("result").type(OBJECT).description("티켓 ID"),
                    fieldWithPath("result.FIRST").type(NUMBER).description("1등 당첨 개수"),
                    fieldWithPath("result.SECOND").type(NUMBER).description("2등 당첨 개수"),
                    fieldWithPath("result.THIRD").type(NUMBER).description("3등 당첨 개수"),
                    fieldWithPath("result.FOURTH").type(NUMBER).description("4등 당첨 개수"),
                    fieldWithPath("result.FIFTH").type(NUMBER).description("5등 당첨 개수"),
                    fieldWithPath("result.NOTHING").type(NUMBER).description("당첨 안된 개수"),
                ),
            )

        every { ticketService.getWinningResult(ticketId, any(), any()) } returns response

        mockMvc.get("/api/count/{id}", ticketId) {
            queryParam("winningNumber", winningNumbers)
            queryParam("bonusNumber", bonusNumber)
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(document)
        }
    }
}
