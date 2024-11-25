package lotto.controller

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.restassured.RestAssured
import lotto.repository.TicketRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseControllerTest : AnnotationSpec() {

    @Autowired
    protected lateinit var ticketRepository: TicketRepository

    @LocalServerPort
    var port: Int = 0

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {
        RestAssured.port = port
    }
}
