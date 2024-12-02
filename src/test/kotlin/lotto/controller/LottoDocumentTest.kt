package lotto.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import lotto.dto.TicketRequest
import lotto.dto.TicketResponse
import lotto.service.TicketService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class)
@AutoConfigureRestDocs
@WebMvcTest(TicketController::class)
class LottoDocumentTest {

    protected lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var ticketService: TicketService

    @BeforeEach
    internal fun setup(
        applicationContext: WebApplicationContext,
        restDocumentationContext: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentationContext)
                    .operationPreprocessors()
                    .withRequestDefaults(
                        Preprocessors.prettyPrint(),
                        Preprocessors.modifyHeaders().remove(HttpHeaders.CONTENT_LENGTH)
                    )
                    .withResponseDefaults(
                        Preprocessors.prettyPrint(),
                        Preprocessors.modifyHeaders().remove(HttpHeaders.CONTENT_LENGTH)
                    )
            )
            .build()
    }

    companion object {
        private val NUMBERS = listOf(listOf(1, 2, 3, 4, 5, 6), listOf(7, 8, 9, 10, 11, 12))
    }

    @Test
    fun `로또 티켓을 생성할 수 있다`() {
        val request = TicketRequest(NUMBERS)
        val response = TicketResponse(1L, NUMBERS)
        val document = document(
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
}
